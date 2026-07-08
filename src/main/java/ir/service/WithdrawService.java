package ir.service;

import ir.service.model.SimpleTransactionResult;

import java.math.BigDecimal;

public interface WithdrawService {
    SimpleTransactionResult withdraw(String accountId, BigDecimal amount);
}