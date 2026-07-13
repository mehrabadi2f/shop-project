package ir.service.analytics;

import ir.dto.event.CouponTriggerEvent;
import ir.model.*;
import ir.model.analytics.*;
import ir.model.product.Product;
import ir.model.enums.CouponRecommendationStatus;
import ir.repository.analytics.CouponCampaignRepository;
import ir.repository.analytics.IssuedCouponRepository;
import ir.repository.analytics.CouponUsageRepository;
import ir.repository.analytics.UserSegmentMembershipRepository;
import ir.repository.analytics.CouponRecommendationSnapshotRepository;
import ir.repository.analytics.UserSegmentMembershipRepository;
import ir.repository.analytics.CouponConversionEventRepository;
import ir.model.enums.CouponScope;
import ir.model.enums.CouponTriggerType;
import ir.exception.CouponAlreadyIssuedException;
import ir.exception.CouponNotEligibleException;
import ir.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponIssuanceService {

    private final CouponCampaignRepository campaignRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final ProductRepository productRepository;
    private final UserSegmentMembershipRepository membershipRepository;
    private final CouponRecommendationSnapshotRepository snapshotRepository;
    private final CouponDecisionService decisionService;
    private final CouponUsageRepository couponUsageRepository;
    private final CouponConversionEventRepository conversionEventRepository;

    public CouponIssuanceService(CouponCampaignRepository campaignRepository,
                                 IssuedCouponRepository issuedCouponRepository,
                                 ProductRepository productRepository,
                                 UserSegmentMembershipRepository membershipRepository,
                                 CouponRecommendationSnapshotRepository snapshotRepository,
                                 CouponDecisionService decisionService,
                                 CouponUsageRepository couponUsageRepository,
                                 CouponConversionEventRepository conversionEventRepository) {
        this.campaignRepository = campaignRepository;
        this.issuedCouponRepository = issuedCouponRepository;
        this.productRepository = productRepository;
        this.membershipRepository = membershipRepository;
        this.snapshotRepository = snapshotRepository;
        this.decisionService = decisionService;
        this.couponUsageRepository = couponUsageRepository;
        this.conversionEventRepository = conversionEventRepository;
    }

    @Transactional
    public void handleTrigger(CouponTriggerEvent event) {
        if (event == null || event.getUserId() == null || event.getTriggerType() == null) {
            return;
        }

        CouponScope scope = resolveScope(event.getTriggerType());
        List<CouponCampaign> campaigns = campaignRepository
                .findByScopeAndActiveTrueAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(
                        scope, ir.model.enums.CampaignStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now()
                );

        List<UserSegmentMembership> memberships = membershipRepository.findByUserIdAndActiveTrue(event.getUserId());

        for (CouponCampaign campaign : campaigns) {
            processCampaignForEvent(event, campaign, memberships);
        }
    }

    private void processCampaignForEvent(CouponTriggerEvent event,
                                         CouponCampaign campaign,
                                         List<UserSegmentMembership> memberships) {

        if (!decisionService.isUserEligibleBySegment(campaign, memberships)) {
            saveSnapshotRejected(event, campaign, null, "User is not eligible by campaign segments");
            return;
        }

        long issuedCount = issuedCouponRepository.countByUserIdAndCampaign_IdAndScope(
                event.getUserId(), campaign.getId(), campaign.getScope());

        if (issuedCount >= campaign.getMaxIssuancePerUser()) {
            saveSnapshotRejected(event, campaign, null, "Max issuance per user reached");
            return;
        }

        if (campaign.getAllowCooldown() != null && Boolean.TRUE.equals(campaign.getAllowCooldown())) {
            boolean inCooldown = issuedCouponRepository.findByUserIdAndActiveTrue(event.getUserId())
                    .stream()
                    .anyMatch(c -> c.getCampaign() != null
                            && c.getCampaign().getId().equals(campaign.getId())
                            && c.getCreatedAt() != null
                            && c.getCreatedAt().isAfter(LocalDateTime.now().minusHours(campaign.getCooldownHours())));
            if (inCooldown) {
                saveSnapshotRejected(event, campaign, null, "Cooldown is active");
                return;
            }
        }

        if (campaign.getScope() == CouponScope.CART) {
            issueCartCoupon(event, campaign);
        } else {
            issueProductCoupon(event, campaign);
        }
    }

    private void issueCartCoupon(CouponTriggerEvent event, CouponCampaign campaign) {
        String issueKey = buildIssueKey(event.getUserId(), event.getCartId(), campaign.getId(), campaign.getScope(), null);
        Optional<IssuedCoupon> existing = issuedCouponRepository.findByIssueKey(issueKey);
        if (existing.isPresent()) {
            saveSnapshotRejected(event, campaign, null, "Duplicate issueKey already exists");
            throw new CouponAlreadyIssuedException("Coupon already issued for this trigger");
        }

        BigDecimal scoreDiscount = decisionService.scoreToDiscountPercent(50);
        BigDecimal finalDiscount = decisionService.chooseFinalDiscountPercent(
                campaign.getCampaignMaxPercent(),
                scoreDiscount,
                BigDecimal.valueOf(100)
        );

        IssuedCoupon coupon = new IssuedCoupon();
        coupon.setUserId(event.getUserId());
        coupon.setCartId(event.getCartId());
        coupon.setCampaign(campaign);
        coupon.setScope(CouponScope.CART);
        coupon.setDiscountPercent(finalDiscount);
        coupon.setIssueKey(issueKey);
        coupon.setCode(generateCouponCode());
        coupon.setActive(true);
        coupon.setExpiresAt(LocalDateTime.now().plusDays(7));

        issuedCouponRepository.save(coupon);
        saveSnapshotActive(event, campaign, null, 50, finalDiscount, "Cart coupon issued successfully");
    }

    private void issueProductCoupon(CouponTriggerEvent event, CouponCampaign campaign) {
        if (event.getRecommendationProductIds() == null || event.getRecommendationProductIds().isEmpty()) {
            saveSnapshotRejected(event, campaign, null, "No recommendation products available");
            return;
        }

        BigDecimal minRequiredProfit = BigDecimal.valueOf(10);

        for (Long productId : event.getRecommendationProductIds()) {
            Product product = productRepository.findById(productId).orElse(null);
            if (!decisionService.isProductEligible(product)) {
                continue;
            }

            BigDecimal grossMargin = decisionService.calculateGrossMargin(product);
            BigDecimal maxSafePercent = decisionService.calculateMaxSafeDiscountPercent(product, minRequiredProfit);
            BigDecimal scoreDiscount = decisionService.scoreToDiscountPercent(50);
            BigDecimal finalDiscount = decisionService.chooseFinalDiscountPercent(
                    campaign.getCampaignMaxPercent(),
                    scoreDiscount,
                    maxSafePercent
            );

            if (finalDiscount.compareTo(BigDecimal.ZERO) <= 0) {
                saveSnapshotRejected(event, campaign, productId, "No safe discount available");
                continue;
            }

            String issueKey = buildIssueKey(event.getUserId(), event.getCartId(), campaign.getId(), campaign.getScope(), productId);
            if (issuedCouponRepository.findByIssueKey(issueKey).isPresent()) {
                saveSnapshotRejected(event, campaign, productId, "Duplicate issueKey already exists");
                continue;
            }

            IssuedCoupon coupon = new IssuedCoupon();
            coupon.setUserId(event.getUserId());
            coupon.setCartId(event.getCartId());
            coupon.setCampaign(campaign);
            coupon.setScope(CouponScope.PRODUCT);
            coupon.setTargetProductId(productId);
            coupon.setDiscountPercent(finalDiscount);
            coupon.setIssueKey(issueKey);
            coupon.setCode(generateCouponCode());
            coupon.setActive(true);
            coupon.setExpiresAt(LocalDateTime.now().plusDays(3));

            issuedCouponRepository.save(coupon);
            saveSnapshotActive(event, campaign, productId, 50, finalDiscount,
                    "Product coupon issued successfully, grossMargin=" + grossMargin);
            return;
        }

        saveSnapshotRejected(event, campaign, null, "No eligible product found");
    }

    private void saveSnapshotActive(CouponTriggerEvent event,
                                    CouponCampaign campaign,
                                    Long productId,
                                    Integer intentScore,
                                    BigDecimal discount,
                                    String reason) {
        CouponRecommendationSnapshot snapshot = new CouponRecommendationSnapshot();
        snapshot.setUserId(event.getUserId());
        snapshot.setCartId(event.getCartId());
        snapshot.setCampaign(campaign);
        snapshot.setScope(campaign.getScope());
        snapshot.setTargetProductId(productId);
        snapshot.setTriggerType(event.getTriggerType());
        snapshot.setStatus(CouponRecommendationStatus.ACTIVE);
        snapshot.setIntentScore(intentScore);
        snapshot.setDiscountPercent(discount);
        snapshot.setReason(reason);
        snapshotRepository.save(snapshot);
    }

    private void saveSnapshotRejected(CouponTriggerEvent event,
                                      CouponCampaign campaign,
                                      Long productId,
                                      String reason) {
        CouponRecommendationSnapshot snapshot = new CouponRecommendationSnapshot();
        snapshot.setUserId(event.getUserId());
        snapshot.setCartId(event.getCartId());
        snapshot.setCampaign(campaign);
        snapshot.setScope(campaign != null ? campaign.getScope() : null);
        snapshot.setTargetProductId(productId);
        snapshot.setTriggerType(event.getTriggerType());
        snapshot.setStatus(CouponRecommendationStatus.REJECTED);
        snapshot.setReason(reason);
        snapshotRepository.save(snapshot);
    }

    private CouponScope resolveScope(CouponTriggerType triggerType) {
        if (triggerType == CouponTriggerType.RECOMMENDATION_VIEWED || triggerType == CouponTriggerType.PRODUCT_VIEWED) {
            return CouponScope.PRODUCT;
        }
        return CouponScope.CART;
    }

    private String buildIssueKey(Long userId, Long cartId, Long campaignId, CouponScope scope, Long productId) {
        return userId + ":" + cartId + ":" + campaignId + ":" + scope + ":" + (productId == null ? "NONE" : productId);
    }

    private String generateCouponCode() {
        return "CPN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public void recordUsage(Long issuedCouponId, Long userId, Long cartId) {
        if (couponUsageRepository.existsByIssuedCouponId(issuedCouponId)) {
            return;
        }
        CouponUsage usage = new CouponUsage();
        usage.setIssuedCouponId(issuedCouponId);
        usage.setUserId(userId);
        usage.setCartId(cartId);
        usage.setStatus(ir.model.enums.CouponUsageStatus.USED);
        usage.setUsedAt(LocalDateTime.now());
        couponUsageRepository.save(usage);

        CouponConversionEvent conversionEvent = new CouponConversionEvent();
        conversionEvent.setIssuedCouponId(issuedCouponId);
        conversionEvent.setUserId(userId);
        conversionEvent.setCartId(cartId);
        conversionEvent.setEventType("COUPON_USED");
        conversionEventRepository.save(conversionEvent);
    }
}
