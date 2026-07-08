package ir.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "درخواست ایجاد حساب بانکی جدید")
public class CreateAccountRequest {

    @NotBlank
    @Schema(description = "نام صاحب حساب", example = "Ali Karimi", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ownerName;

    @Schema(description = "آیدی یوزر", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userid;

    @NotNull
    @DecimalMin(value = "0.00", message = "موجودی اولیه نمی‌تواند منفی باشد")
    @Schema(description = "موجودی اولیه حساب", example = "250000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal initialBalance;

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }

    public Long getUserid() { return userid; }
    public void setUserid(Long userid) { this.userid = userid; }
}
