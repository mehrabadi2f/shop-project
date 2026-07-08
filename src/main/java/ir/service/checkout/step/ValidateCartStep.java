package ir.service.checkout.step;

import ir.service.checkout.CheckoutContext;
import org.springframework.stereotype.Component;

@Component
public class ValidateCartStep implements CheckoutStep {

    @Override
    public void process(CheckoutContext context) {

        if (context.getCartItems().isEmpty()) {
            throw new RuntimeException("cart empty");
        }
    }
}
