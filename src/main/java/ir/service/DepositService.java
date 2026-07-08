package ir.service;


import ir.service.model.SimpleTransactionResult;

import java.math.BigDecimal;

public interface DepositService {
    SimpleTransactionResult deposit(String accountId, BigDecimal amount);
}
