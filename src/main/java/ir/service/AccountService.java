package ir.service;


import ir.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account getAccountById(String accountId);
    /**
     * ایجاد حساب جدید با نام مالک و موجودی اولیه.
     *
     * @param ownerName      نام مالک حساب
     * @param initialBalance موجودی اولیه حساب
     * @param userid
     * @return حساب ایجادشده
     */
    Account createAccount(String ownerName, BigDecimal initialBalance, Long userid);
    List<Account> getAllAccounts();
}
