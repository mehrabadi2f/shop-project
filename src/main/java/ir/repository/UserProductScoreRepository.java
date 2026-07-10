package ir.repository;

import ir.model.analytics.UserProductScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductScoreRepository extends JpaRepository<UserProductScore, Long> {

    List<UserProductScore> findByUserIdOrderByScoreDesc(Long userId);

    void deleteByUserId(Long userId);
}
