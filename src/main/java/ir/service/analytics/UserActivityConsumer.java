package ir.service.analytics;

import ir.dto.event.UserActivityEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserActivityConsumer {

    private final UserActivityPersistenceService persistenceService;
    @KafkaListener(
            topics = "user-activities-topic",
            groupId = "user-activities-group"
    )
    public void consume(UserActivityEvent event) {
        persistenceService.save(event);
    }
}
