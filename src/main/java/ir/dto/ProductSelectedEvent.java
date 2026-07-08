package ir.dto;

import java.time.LocalDateTime;

public record ProductSelectedEvent(
        Long userId,
        Long productId,
        Integer quantity,
        LocalDateTime timestamp
) {}