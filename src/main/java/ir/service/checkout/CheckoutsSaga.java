package ir.service.checkout;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import  ir.service.checkout.step.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutsSaga {

      private final ReserveStockStep reserveStockStep;
    private final CreateOrderStep createOrderStep;
  //  private final CreatePaymentStep createPaymentStep;
  // private final ConfirmPaymentStep confirmPaymentStep;

    public void execute(CheckoutContext context) {

        List<CheckoutSaga> completedSteps = new ArrayList<>();

        try {

            reserveStockStep.execute(context);
            completedSteps.add(reserveStockStep);

             createOrderStep.execute(context);
            completedSteps.add(createOrderStep);

           // createPaymentStep.execute(context);
          //   completedSteps.add(createPaymentStep);

           //  confirmPaymentStep.execute(context);
           //  completedSteps.add(confirmPaymentStep);

        }

        catch (Exception e) {

            Collections.reverse(completedSteps);

         //   for ( step : completedSteps) {

           //     step.compensate(context);

          //  }

            throw e;
        }

    }
}
