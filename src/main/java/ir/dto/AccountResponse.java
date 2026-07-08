package ir.kimia.energyupload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "مدل خروجی اطلاعات حساب کاربر")
public record AccountResponse(
        @Schema(description = "شناسه حساب", example = "ACC-1001")
        String id,

        @Schema(description = "نام صاحب حساب", example = "Ali Rezaei")
        String ownerName,

        @Schema(description = "موجودی حساب", example = "125000.00")
        BigDecimal balance
) {


        public AccountResponse(String id, String ownerName, BigDecimal balance) {
                this.id = id ;
                this.ownerName = ownerName;
                this.balance = balance;
        }

        public String getAccountId() { return id; }
        public String getOwnerName() { return ownerName; }
        public BigDecimal getBalance() { return balance; }
}
