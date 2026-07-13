package ir.repository.analytics;


import ir.model.analytics.CouponConversionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponConversionEventRepository extends JpaRepository<CouponConversionEvent, Long> {
}
