package ir.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "پاسخ سرویس ایجاد حساب جدید")
public class CreateAccountResponse {

    @Schema(description = "شناسه یکتای حساب ایجادشده", example = "ACC-1001")
    private String accountId;

    @Schema(description = "نام مالک حساب", example = "Ali Karimi")
    private String ownerName;

    @Schema(description = "موجودی اولیه حساب", example = "250000.00")
    private BigDecimal balance;

    public CreateAccountResponse(String accountId, String ownerName, BigDecimal balance) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = balance;
    }
public void setAccountId(String accountId) { this.accountId = accountId; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getAccountId() { return accountId; }
    public String getOwnerName() { return ownerName; }
    public BigDecimal getBalance() { return balance; }
}
