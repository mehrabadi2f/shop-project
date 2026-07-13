package ir.service.analytics;


import ir.dto.event.CouponTriggerEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CouponEventPublisher {

    private final ApplicationEventPublisher publisher;

    public CouponEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(CouponTriggerEvent event) {
        publisher.publishEvent(event);
    }
}
