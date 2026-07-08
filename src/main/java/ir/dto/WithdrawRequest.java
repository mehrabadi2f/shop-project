package ir.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "درخواست برداشت وجه از حساب کاربر")
public class WithdrawRequest {

    @NotBlank
    @Schema(description = "شناسه حسابی که از آن برداشت انجام می‌شود", example = "ACC-1001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountId;

    @NotNull
    @DecimalMin(value = "1.00", message = "حداقل مبلغ برداشت باید حداقل ۱ واحد پولی باشد")
    @Schema(description = "مبلغ موردنظر برای برداشت", example = "100000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
