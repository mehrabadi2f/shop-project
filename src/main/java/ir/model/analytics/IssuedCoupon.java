package ir.model.analytics;


import ir.model.enums.CouponScope;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "issued_coupon",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_issued_coupon_issue_key", columnNames = {"issue_key"}),
                @UniqueConstraint(name = "uk_issued_coupon_user_campaign_scope", columnNames = {"user_id", "campaign_id", "scope"})
        })
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private CouponCampaign campaign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponScope scope;

    @Column(name = "target_product_id")
    private Long targetProductId;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "issue_key", nullable = false, unique = true)
    private String issueKey;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public IssuedCoupon() {
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

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public BigDecimal getDiscountPercent() { return discountPercent; }

    public void setDiscountPercent(BigDecimal discountPercent) { this.discountPercent = discountPercent; }

    public String getIssueKey() { return issueKey; }

    public void setIssueKey(String issueKey) { this.issueKey = issueKey; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getExpiresAt() { return expiresAt; }

    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
