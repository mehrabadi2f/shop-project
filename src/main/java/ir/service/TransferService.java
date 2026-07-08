package ir.service;

import ir.service.model.TransferResult;

import java.math.BigDecimal;

public interface TransferService {
    TransferResult transfer(String fromAccountId, String toAccountId, BigDecimal amount);
}
