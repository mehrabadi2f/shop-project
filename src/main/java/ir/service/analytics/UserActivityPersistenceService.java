package ir.service.analytics;


import ir.dto.event.UserActivityEvent;
import ir.model.analytics.UserActivity;
import ir.repository.analytics.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserActivityPersistenceService {

    private final UserActivityRepository userActivityRepository;

    public void save(UserActivityEvent event) {
        UserActivity activity = new UserActivity();
        activity.setUserId(event.userId());
        activity.setProductId(event.productId());
        activity.setEventType(event.eventType());
        activity.setMetadata(event.metadata());
        activity.setCreatedAt(event.timestamp());

        userActivityRepository.save(activity);
    }
}
