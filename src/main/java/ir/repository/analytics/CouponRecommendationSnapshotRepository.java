package ir.repository.analytics;

import ir.model.analytics.CouponRecommendationSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRecommendationSnapshotRepository extends JpaRepository<CouponRecommendationSnapshot, Long> {
}
