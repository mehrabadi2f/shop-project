package ir.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "درخواست واریز وجه به حساب")
public class DepositRequest {

    @Schema(description = "شناسه حساب مقصد", example = "ACC-1001", required = true)
    private String accountId;

    @Schema(description = "مبلغ واریزی", example = "25000.00", required = true)
    private BigDecimal amount;

    // Getters and Setters
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
