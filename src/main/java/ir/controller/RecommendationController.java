package ir.controller;

import ir.dto.rec.RecommendedProductDto;
import ir.service.analytics.RecommendationQueryService;
import ir.service.analytics.RecommendationScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationQueryService recommendationQueryService;
    private final RecommendationScoreService recommendationScoreService;

    @PostMapping("/rebuild/{userId}")
    public String rebuildUserRecommendations(@PathVariable Long userId) {
        recommendationScoreService.rebuildScoresForUser(userId);
        return "Recommendations rebuilt for userId = " + userId;
    }

    @PostMapping("/rebuild-all")
    public String rebuildAllRecommendations() {
        recommendationScoreService.rebuildScoresForAllUsers();
        return "Recommendations rebuilt for all users";
    }

    @GetMapping("/{userId}")
    public List<RecommendedProductDto> getRecommendations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return recommendationQueryService.getRecommendations(userId, limit);
    }
}
