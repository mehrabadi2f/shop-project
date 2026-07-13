package ir.repository.analytics;


import ir.model.analytics.IssuedCoupon;
import ir.model.enums.CouponScope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {
    Optional<IssuedCoupon> findByIssueKey(String issueKey);

    long countByUserIdAndCampaign_IdAndScope(Long userId, Long campaignId, CouponScope scope);

    List<IssuedCoupon> findByUserIdAndActiveTrue(Long userId);
}
