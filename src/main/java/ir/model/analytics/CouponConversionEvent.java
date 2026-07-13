package ir.model.analytics;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_conversion_event")
public class CouponConversionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long campaignId;

    private Long userId;

    private Long cartId;

    private Long issuedCouponId;

    private String eventType;

    private LocalDateTime occurredAt = LocalDateTime.now();

    public CouponConversionEvent() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getCampaignId() { return campaignId; }

    public void setCampaignId(Long campaignId) { this.campaignId = campaignId; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCartId() { return cartId; }

    public void setCartId(Long cartId) { this.cartId = cartId; }

    public Long getIssuedCouponId() { return issuedCouponId; }

    public void setIssuedCouponId(Long issuedCouponId) { this.issuedCouponId = issuedCouponId; }

    public String getEventType() { return eventType; }

    public void setEventType(String eventType) { this.eventType = eventType; }

    public LocalDateTime getOccurredAt() { return occurredAt; }

    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
