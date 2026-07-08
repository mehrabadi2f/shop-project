package ir.service.checkout;

import ir.service.checkout.step.CheckoutStep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckoutPipeline {

    private final List<CheckoutStep> steps;

    public CheckoutPipeline(List<CheckoutStep> steps) {
        this.steps = steps;
    }

    @Transactional
    public void execute(CheckoutContext context) {

        for (CheckoutStep step : steps) {
            step.process(context);
        }
    }
}
