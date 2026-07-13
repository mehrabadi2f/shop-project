package ir.repository.analytics;


import ir.model.analytics.CouponCampaign;
import ir.model.enums.CampaignStatus;
import ir.model.enums.CouponScope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponCampaignRepository extends JpaRepository<CouponCampaign, Long> {
    List<CouponCampaign> findByScopeAndActiveTrueAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(
            CouponScope scope,
            CampaignStatus status,
            LocalDateTime now1,
            LocalDateTime now2
    );
}
