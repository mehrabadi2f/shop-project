package ir.service.model;


import java.math.BigDecimal;

public class SimpleTransactionResult {

    private Long transactionId;
    private BigDecimal newBalance;

    public SimpleTransactionResult(Long transactionId, BigDecimal newBalance) {
        this.transactionId = transactionId;
        this.newBalance = newBalance;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }
}
