package ir.controller;
import ir.dto.event.UserActivityEvent;
import ir.model.analytics.ActivityType;
import ir.service.analytics.UserActivityPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityPublisher publisher;

    // ۱. ثبت کلیک روی محصول
    @PostMapping("/click")
    public ResponseEntity<Void> logClick(@RequestParam Long userId, @RequestParam Long productId) {
        publisher.publish(new UserActivityEvent(userId, productId, ActivityType.CLICK, null, LocalDateTime.now()));
        return ResponseEntity.ok().build();
    }

    // ۲. ثبت سرچ
    @PostMapping("/search")
    public ResponseEntity<Void> logSearch(@RequestParam Long userId, @RequestParam String query, @RequestParam(required = false) Long matchedProductId) {
        // matchedProductId محصولی است که کاربر احتمالاً بعد از سرچ مستقیم روی آن کلیک کرده یا در نتایج اول بوده
        publisher.publish(new UserActivityEvent(userId, matchedProductId, ActivityType.SEARCH, query, LocalDateTime.now()));
        return ResponseEntity.ok().build();
    }
}

