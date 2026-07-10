package ir.service.analytics;

import ir.model.analytics.UserActivity;
import ir.model.analytics.UserProductScore;
import ir.service.analytics.ActivityWeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ir.repository.UserActivityRepository;
import ir.repository.UserProductScoreRepository;

@Service
@RequiredArgsConstructor
public class RecommendationScoreService {

    private final UserActivityRepository userActivityRepository;
    private final UserProductScoreRepository userProductScoreRepository;
    private final ActivityWeightService activityWeightService;

    @Transactional
    public void rebuildScoresForUser(Long userId) {
        List<UserActivity> activities = userActivityRepository.findByUserIdAndProductIdIsNotNull(userId);

        Map<Long, Double> scoreByProduct = new HashMap<>();
        Map<Long, LocalDateTime> lastInteractionByProduct = new HashMap<>();

        for (UserActivity activity : activities) {
            Long productId = activity.getProductId();
            double weight = activityWeightService.getWeight(activity.getEventType());

            scoreByProduct.merge(productId, weight, Double::sum);

            LocalDateTime currentLast = lastInteractionByProduct.get(productId);
            if (currentLast == null || activity.getCreatedAt().isAfter(currentLast)) {
                lastInteractionByProduct.put(productId, activity.getCreatedAt());
            }
        }

        userProductScoreRepository.deleteByUserId(userId);

        for (Map.Entry<Long, Double> entry : scoreByProduct.entrySet()) {
            Long productId = entry.getKey();
            Double score = entry.getValue();

            UserProductScore ups = new UserProductScore();
            ups.setUserId(userId);
            ups.setProductId(productId);
            ups.setScore(score);
            ups.setLastInteractionAt(lastInteractionByProduct.get(productId));

            userProductScoreRepository.save(ups);
        }
    }

    @Transactional
    public void rebuildScoresForAllUsers() {
        List<UserActivity> allActivities = userActivityRepository.findAll();

        Map<Long, Map<Long, Double>> userProductScores = new HashMap<>();
        Map<Long, Map<Long, LocalDateTime>> userProductLastTimes = new HashMap<>();

        for (UserActivity activity : allActivities) {
            if (activity.getProductId() == null) {
                continue;
            }

            Long userId = activity.getUserId();
            Long productId = activity.getProductId();
            double weight = activityWeightService.getWeight(activity.getEventType());

            userProductScores
                    .computeIfAbsent(userId, k -> new HashMap<>())
                    .merge(productId, weight, Double::sum);

            userProductLastTimes
                    .computeIfAbsent(userId, k -> new HashMap<>());

            LocalDateTime currentLast = userProductLastTimes.get(userId).get(productId);
            if (currentLast == null || activity.getCreatedAt().isAfter(currentLast)) {
                userProductLastTimes.get(userId).put(productId, activity.getCreatedAt());
            }
        }

        userProductScoreRepository.deleteAll();

        for (Map.Entry<Long, Map<Long, Double>> userEntry : userProductScores.entrySet()) {
            Long userId = userEntry.getKey();
            Map<Long, Double> productScores = userEntry.getValue();

            for (Map.Entry<Long, Double> productEntry : productScores.entrySet()) {
                Long productId = productEntry.getKey();
                Double score = productEntry.getValue();

                UserProductScore ups = new UserProductScore();
                ups.setUserId(userId);
                ups.setProductId(productId);
                ups.setScore(score);
                ups.setLastInteractionAt(userProductLastTimes.get(userId).get(productId));

                userProductScoreRepository.save(ups);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<UserProductScore> getTopScoresForUser(Long userId, int limit) {
        return userProductScoreRepository.findByUserIdOrderByScoreDesc(userId)
                .stream()
                .sorted(Comparator.comparing(UserProductScore::getScore).reversed())
                .limit(limit)
                .toList();
    }
}
