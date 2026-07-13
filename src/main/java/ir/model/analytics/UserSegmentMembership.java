package ir.model.analytics;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_segment_membership",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_segment_membership", columnNames = {"user_id", "segment_id"}))
public class UserSegmentMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id", nullable = false)
    private userSegment segment;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    public UserSegmentMembership() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public userSegment getSegment() { return segment; }

    public void setSegment(userSegment segment) { this.segment = segment; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getValidFrom() { return validFrom; }

    public void setValidFrom(LocalDateTime validFrom) { this.validFrom = validFrom; }

    public LocalDateTime getValidTo() { return validTo; }

    public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }
}
