package ir.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "پاسخ سرویس برداشت وجه")
public class WithdrawResponse {

    @Schema(description = "شناسه یکتای تراکنش برداشت", example = "902145")
    private Long transactionId;

    @Schema(description = "مانده حساب پس از برداشت", example = "450000.50")
    private BigDecimal newBalance;

    public WithdrawResponse(Long transactionId, BigDecimal newBalance) {
        this.transactionId = transactionId;
        this.newBalance = newBalance;
    }

    public Long getTransactionId() { return transactionId; }
    public BigDecimal getNewBalance() { return newBalance; }
}
