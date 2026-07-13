package ir.service.analytics;

import ir.model.analytics.CouponCampaign;
import ir.model.product.Product;
import ir.model.analytics.UserSegmentMembership;
import ir.model.enums.CouponScope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CouponDecisionService {

    public boolean isCampaignWithinScope(CouponCampaign campaign, CouponScope scope) {
        return campaign != null && campaign.getScope() == scope;
    }

    public boolean isUserEligibleBySegment(CouponCampaign campaign, List<UserSegmentMembership> memberships) {
        if (campaign.getTargetSegments() == null || campaign.getTargetSegments().isEmpty()) {
            return true;
        }
        if (memberships == null || memberships.isEmpty()) {
            return false;
        }

        Set<Long> userSegmentIds = memberships.stream()
                .filter(UserSegmentMembership::getActive)
                .filter(m -> m.getSegment() != null && Boolean.TRUE.equals(m.getSegment().getActive()))
                .filter(this::isMembershipValidNow)
                .map(m -> m.getSegment().getId())
                .collect(Collectors.toSet());

        return campaign.getTargetSegments().stream()
                .anyMatch(seg -> Boolean.TRUE.equals(seg.getActive()) && userSegmentIds.contains(seg.getId()));
    }

    private boolean isMembershipValidNow(UserSegmentMembership m) {
        var now = java.time.LocalDateTime.now();
        return (m.getValidFrom() == null || !m.getValidFrom().isAfter(now))
                && (m.getValidTo() == null || !m.getValidTo().isBefore(now));
    }

    public BigDecimal calculateGrossMargin(Product product) {
        if (product == null || product.getSalePrice() == null || product.getCostPrice() == null) {
            return BigDecimal.ZERO;
        }
        return product.getSalePrice().subtract(product.getCostPrice());
    }

    public BigDecimal calculateMaxSafeDiscountAmount(Product product, BigDecimal minRequiredProfit) {
        if (product == null) return BigDecimal.ZERO;
        return product.getSalePrice()
                .subtract(product.getCostPrice())
                .subtract(minRequiredProfit);
    }

    public BigDecimal calculateMaxSafeDiscountPercent(Product product, BigDecimal minRequiredProfit) {
        if (product == null || product.getSalePrice() == null || product.getSalePrice().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal amount = calculateMaxSafeDiscountAmount(product, minRequiredProfit);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return amount.divide(product.getSalePrice(), 4, java.math.RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal scoreToDiscountPercent(Integer intentScore) {
        if (intentScore == null) return BigDecimal.ZERO;
        if (intentScore >= 50) return BigDecimal.valueOf(15);
        if (intentScore >= 25) return BigDecimal.valueOf(10);
        if (intentScore >= 10) return BigDecimal.valueOf(5);
        return BigDecimal.ZERO;
    }

    public BigDecimal chooseFinalDiscountPercent(BigDecimal campaignMaxPercent,
                                                 BigDecimal scoreDiscountPercent,
                                                 BigDecimal maxSafeDiscountPercent) {
        BigDecimal a = campaignMaxPercent == null ? BigDecimal.ZERO : campaignMaxPercent;
        BigDecimal b = scoreDiscountPercent == null ? BigDecimal.ZERO : scoreDiscountPercent;
        BigDecimal c = maxSafeDiscountPercent == null ? BigDecimal.ZERO : maxSafeDiscountPercent;
        return a.min(b).min(c);
    }

    public boolean isProductEligible(Product product) {
        return product != null
                && Boolean.TRUE.equals(product.getActive())
                && product.getStock() != null
                && product.getStock() > 0
                && product.getSalePrice() != null
                && product.getCostPrice() != null
                && product.getSalePrice().compareTo(product.getCostPrice()) > 0;
    }
}
