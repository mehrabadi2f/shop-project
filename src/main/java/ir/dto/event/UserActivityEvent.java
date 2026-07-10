package ir.dto.event;

import ir.model.analytics.ActivityType;
import java.time.LocalDateTime;

public record UserActivityEvent(
        Long userId,
        Long productId,
        ActivityType eventType,
        String metadata, // مثلاً کلمه سرچ شده
        LocalDateTime timestamp
) {}
