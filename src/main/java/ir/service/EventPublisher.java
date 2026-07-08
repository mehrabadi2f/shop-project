package ir.service;
import  ir.dto.ProductSelectedEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "user-product-interactions";

    public void publishProductSelected(Long userId, Long productId, Integer quantity) {
        ProductSelectedEvent event = new ProductSelectedEvent(userId, productId, quantity, LocalDateTime.now());
        kafkaTemplate.send(TOPIC, userId.toString(), event);
    }
}
