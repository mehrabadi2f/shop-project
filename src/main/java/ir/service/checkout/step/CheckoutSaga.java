package ir.service.checkout.step;

import ir.service.checkout.CheckoutContext;

public interface  CheckoutSaga {
    void execute(CheckoutContext context);

    void compensate(CheckoutContext context);

}
