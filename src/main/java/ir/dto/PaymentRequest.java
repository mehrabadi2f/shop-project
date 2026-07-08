package ir.kimia.energyupload.dto;

import java.math.BigDecimal;

public record PaymentRequest(
        Long paymentId,
        BigDecimal amount,
        String callbackUrl
) {}

