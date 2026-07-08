package ir.service.payment;

import ir.kimia.energyupload.dto.PaymentRequest;
import ir.kimia.energyupload.dto.PaymentResponse;
import ir.kimia.energyupload.dto.PaymentVerification;

public interface PaymentGateway {

   PaymentResponse requestPayment(PaymentRequest request);

    PaymentVerification verify(String referenceId);

}