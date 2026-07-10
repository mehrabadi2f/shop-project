package ir.service.analytics;

import ir.dto.rec.RecommendedProductDto;
import ir.model.analytics.UserProductScore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationQueryService {

    private final RecommendationScoreService recommendationScoreService;

    public List<RecommendedProductDto> getRecommendations(Long userId, int limit) {
        List<UserProductScore> scores = recommendationScoreService.getTopScoresForUser(userId, limit);

        return scores.stream()
                .map(score -> new RecommendedProductDto(
                        score.getProductId(),
                        score.getScore()
                ))
                .toList();
    }
}
