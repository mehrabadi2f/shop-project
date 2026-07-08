package ir.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "پاسخ عملیات واریز")
public class DepositResponse {

    @Schema(description = "شناسه تراکنش واریز", example = "TRX-872009")
    private Long transactionId;

    @Schema(description = "موجودی جدید حساب پس از واریز", example = "150000.50")
    private BigDecimal newBalance;

    public DepositResponse(long transactionId, BigDecimal newBalance) {
        this.transactionId = transactionId;
        this.newBalance = newBalance;
    }

    public long getTransactionId() { return transactionId; }
    public BigDecimal getNewBalance() { return newBalance; }
}
