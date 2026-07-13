package ir.model.analytics;


import ir.model.enums.CouponRecommendationStatus;
import ir.model.enums.CouponScope;
import ir.model.enums.CouponTriggerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_recommendation_snapshot")
public class CouponRecommendationSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private CouponCampaign campaign;

    @Enumerated(EnumType.STRING)
    private CouponScope scope;

    private Long targetProductId;

    @Enumerated(EnumType.STRING)
    private CouponTriggerType triggerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponRecommendationStatus status;

    private Integer intentScore;

    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercent;

    @Column(length = 1000)
    private String reason;

    private LocalDateTime createdAt = LocalDateTime.now();

    public CouponRecommendationSnapshot() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCartId() { return cartId; }

    public void setCartId(Long cartId) { this.cartId = cartId; }

    public CouponCampaign getCampaign() { return campaign; }

    public void setCampaign(CouponCampaign campaign) { this.campaign = campaign; }

    public CouponScope getScope() { return scope; }

    public void setScope(CouponScope scope) { this.scope = scope; }

    public Long getTargetProductId() { return targetProductId; }

    public void setTargetProductId(Long targetProductId) { this.targetProductId = targetProductId; }

    public CouponTriggerType getTriggerType() { return triggerType; }

    public void setTriggerType(CouponTriggerType triggerType) { this.triggerType = triggerType; }

    public CouponRecommendationStatus getStatus() { return status; }

    public void setStatus(CouponRecommendationStatus status) { this.status = status; }

    public Integer getIntentScore() { return intentScore; }

    public void setIntentScore(Integer intentScore) { this.intentScore = intentScore; }

    public BigDecimal getDiscountPercent() { return discountPercent; }

    public void setDiscountPercent(BigDecimal discountPercent) { this.discountPercent = discountPercent; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
