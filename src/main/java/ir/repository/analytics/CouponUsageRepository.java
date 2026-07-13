package ir.repository.analytics;


import ir.model.analytics.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {
    boolean existsByIssuedCouponId(Long issuedCouponId);
}
