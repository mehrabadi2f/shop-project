package ir.service.analytics;

import ir.dto.event.UserActivityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserActivityPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "user-activities-topic";

    public void publish(UserActivityEvent event) {
        // ارسال غیرمسدودکننده (Fire and Forget)
        kafkaTemplate.send(TOPIC, event.userId().toString(), event);
    }
}
