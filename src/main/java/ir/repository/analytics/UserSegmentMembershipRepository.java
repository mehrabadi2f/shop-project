package ir.repository.analytics;

import ir.model.analytics.UserSegmentMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSegmentMembershipRepository extends JpaRepository<UserSegmentMembership, Long> {
    List<UserSegmentMembership> findByUserIdAndActiveTrue(Long userId);
}
