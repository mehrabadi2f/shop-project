package ir.service.checkout.step;

import ir.service.checkout.CheckoutContext;

public interface CheckoutStep {

    void process(CheckoutContext context);
}
