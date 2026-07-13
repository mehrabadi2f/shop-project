package ir.model.analytics;

import ir.model.enums.SegmentType;
import jakarta.persistence.*;

@Entity
@Table(name = "user_segment")
public class userSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SegmentType type;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    public userSegment() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public SegmentType getType() { return type; }

    public void setType(SegmentType type) { this.type = type; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }
}
