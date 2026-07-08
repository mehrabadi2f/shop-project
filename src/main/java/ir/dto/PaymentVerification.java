package ir.kimia.energyupload.dto;

public record PaymentVerification(
        boolean success,
        String referenceId
) {}
