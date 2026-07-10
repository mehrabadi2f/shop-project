package ir.service.analytics;


import ir.model.analytics.ActivityType;
import org.springframework.stereotype.Component;

@Component
public class ActivityWeightService {

    public double getWeight(ActivityType type) {
        if (type == null) {
            return 0.0;
        }

        return switch (type) {
            case CLICK -> 2.0;
            case SEARCH -> 1.0;
            case ADD_TO_CART -> 5.0;
            case PURCHASE -> 10.0;
        };
    }
}

