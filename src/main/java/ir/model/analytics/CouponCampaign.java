package ir.model.analytics;


import ir.model.enums.CampaignStatus;
import ir.model.enums.CouponScope;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coupon_campaign")
public class CouponCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponScope scope;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal campaignMaxPercent;

    @Column(nullable = false)
    private Integer minRequiredIntentScore;

    @Column(nullable = false)
    private Integer maxIssuancePerUser;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status = CampaignStatus.ACTIVE;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Column(nullable = false)
    private Boolean allowCooldown = Boolean.TRUE;

    @Column(nullable = false)
    private Integer cooldownHours = 24;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "coupon_campaign_target_segment",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "segment_id")
    )
    private Set<userSegment> targetSegments = new HashSet<>();

    public CouponCampaign() {
    }

    // getters and setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public CouponScope getScope() { return scope; }

    public void setScope(CouponScope scope) { this.scope = scope; }

    public BigDecimal getCampaignMaxPercent() { return campaignMaxPercent; }

    public void setCampaignMaxPercent(BigDecimal campaignMaxPercent) { this.campaignMaxPercent = campaignMaxPercent; }

    public Integer getMinRequiredIntentScore() { return minRequiredIntentScore; }

    public void setMinRequiredIntentScore(Integer minRequiredIntentScore) { this.minRequiredIntentScore = minRequiredIntentScore; }

    public Integer getMaxIssuancePerUser() { return maxIssuancePerUser; }

    public void setMaxIssuancePerUser(Integer maxIssuancePerUser) { this.maxIssuancePerUser = maxIssuancePerUser; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public CampaignStatus getStatus() { return status; }

    public void setStatus(CampaignStatus status) { this.status = status; }

    public LocalDateTime getStartAt() { return startAt; }

    public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }

    public LocalDateTime getEndAt() { return endAt; }

    public void setEndAt(LocalDateTime endAt) { this.endAt = endAt; }

    public Boolean getAllowCooldown() { return allowCooldown; }

    public void setAllowCooldown(Boolean allowCooldown) { this.allowCooldown = allowCooldown; }

    public Integer getCooldownHours() { return cooldownHours; }

    public void setCooldownHours(Integer cooldownHours) { this.cooldownHours = cooldownHours; }

    public Set<userSegment> getTargetSegments() { return targetSegments; }

    public void setTargetSegments(Set<userSegment> targetSegments) { this.targetSegments = targetSegments; }
}
