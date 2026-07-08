package ir.service.payment;

import ir.kimia.energyupload.dto.PaymentRequest;
import ir.kimia.energyupload.dto.PaymentResponse;
import ir.kimia.energyupload.dto.PaymentVerification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FakeGateway implements PaymentGateway {

    @Override
    public PaymentResponse requestPayment(PaymentRequest request) {

        String authority = UUID.randomUUID().toString();

        String paymentUrl =
                "http://localhost:8081/fake-gateway/pay/" + authority;

        return new PaymentResponse(paymentUrl, authority);
    }

    @Override
    public PaymentVerification verify(String authority) {

        return new PaymentVerification(
                true,
                "FAKE-REF-" + authority
        );
    }
}
