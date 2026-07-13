package ir.controller.analytics;

import ir.dto.event.CouponTriggerEvent;
import ir.service.analytics.CouponEventPublisher;
import ir.service.analytics.CouponIssuanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
public class CouponCommandController {

    private final CouponEventPublisher publisher;
    private final CouponIssuanceService issuanceService;

    public CouponCommandController(CouponEventPublisher publisher, CouponIssuanceService issuanceService) {
        this.publisher = publisher;
        this.issuanceService = issuanceService;
    }

    @PostMapping("/trigger")
    public ResponseEntity<String> trigger(@RequestBody CouponTriggerEvent event) {
        publisher.publish(event);
        return ResponseEntity.accepted().body("Coupon trigger event accepted");
    }

    @PostMapping("/usage")
    public ResponseEntity<String> useCoupon(@RequestParam Long issuedCouponId,
                                            @RequestParam Long userId,
                                            @RequestParam Long cartId) {
        issuanceService.recordUsage(issuedCouponId, userId, cartId);
        return ResponseEntity.ok("Coupon usage recorded");
    }
}
