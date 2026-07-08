package ir.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class TransferRequest {

    @NotBlank
    private String fromAccountId;

    @NotBlank
    private String toAccountId;

    @Min(1)
    private BigDecimal amount;

    public String getFromAccountId() { return fromAccountId; }

    public void setFromAccountId(String fromAccountId) { this.fromAccountId = fromAccountId; }

    public String getToAccountId() { return toAccountId; }

    public void setToAccountId(String toAccountId) { this.toAccountId = toAccountId; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }
}

