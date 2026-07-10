package ir.repository;

import ir.model.analytics.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    List<UserActivity> findByUserId(Long userId);
    List<UserActivity> findByUserIdAndProductIdIsNotNull(Long userId);
}
