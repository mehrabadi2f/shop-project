package ir.service.checkout.step;

import ir.service.WithdrawService;
import ir.service.checkout.CheckoutContext;
import org.springframework.stereotype.Component;

@Component
public class WithdrawWalletStep implements CheckoutStep {

    private final WithdrawService withdrawService;

    public WithdrawWalletStep(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @Override
    public void process(CheckoutContext context) {

        withdrawService.withdraw(
                context.getUserId(),
                context.getTotalAmount()
        );
    }
}
