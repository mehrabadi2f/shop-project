package ir.service.analytics;



import ir.dto.event.CouponTriggerEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CouponTriggerEventConsumer {

    private final CouponIssuanceService issuanceService;

    public CouponTriggerEventConsumer(CouponIssuanceService issuanceService) {
        this.issuanceService = issuanceService;
    }

    @EventListener
    public void onCouponTriggerEvent(CouponTriggerEvent event) {
        issuanceService.handleTrigger(event);
    }
}
