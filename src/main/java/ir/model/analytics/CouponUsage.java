package ir.model.analytics;

import ir.model.enums.CouponUsageStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_usage")
public class CouponUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long issuedCouponId;

    private Long userId;

    private Long cartId;

    @Enumerated(EnumType.STRING)
    private CouponUsageStatus status;

    private LocalDateTime usedAt;

    private LocalDateTime createdAt = LocalDateTime.now();

    public CouponUsage() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getIssuedCouponId() { return issuedCouponId; }

    public void setIssuedCouponId(Long issuedCouponId) { this.issuedCouponId = issuedCouponId; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCartId() { return cartId; }

    public void setCartId(Long cartId) { this.cartId = cartId; }

    public CouponUsageStatus getStatus() { return status; }

    public void setStatus(CouponUsageStatus status) { this.status = status; }

    public LocalDateTime getUsedAt() { return usedAt; }

    public void setUsedAt(LocalDateTime usedAt) { this.usedAt = usedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
