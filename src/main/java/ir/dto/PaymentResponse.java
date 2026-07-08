package ir.kimia.energyupload.dto;

public record PaymentResponse(
        String paymentUrl,
        String authority
) {}

