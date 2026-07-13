package ir.dto.event;

import ir.model.enums.CouponTriggerType;

import java.time.LocalDateTime;
import java.util.List;

public class CouponTriggerEvent {

    private Long userId;
    private Long cartId;
    private CouponTriggerType triggerType;
    private List<Long> recommendationProductIds;
    private LocalDateTime occurredAt;

    public CouponTriggerEvent() {
    }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCartId() { return cartId; }

    public void setCartId(Long cartId) { this.cartId = cartId; }

    public CouponTriggerType getTriggerType() { return triggerType; }

    public void setTriggerType(CouponTriggerType triggerType) { this.triggerType = triggerType; }

    public List<Long> getRecommendationProductIds() { return recommendationProductIds; }

    public void setRecommendationProductIds(List<Long> recommendationProductIds) { this.recommendationProductIds = recommendationProductIds; }

    public LocalDateTime getOccurredAt() { return occurredAt; }

    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
