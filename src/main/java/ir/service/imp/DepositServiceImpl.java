package ir.service.imp;

import ir.model.Account;
import ir.model.LedgerEntry;
import ir.exception.AccountNotFoundException;
import ir.repository.AccountRepository;
import ir.repository.LedgerEntryRepository;
import ir.service.AccountLockService;
import ir.service.DepositService;
import ir.service.model.SimpleTransactionResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class DepositServiceImpl implements DepositService {

    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountLockService accountLockService;

    public DepositServiceImpl(AccountRepository accountRepository,
                              LedgerEntryRepository ledgerEntryRepository,
                              AccountLockService accountLockService) {
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.accountLockService = accountLockService;
    }

    @Override
    @Transactional
    public SimpleTransactionResult deposit(String accountId, BigDecimal amount) {

        accountLockService.lock(accountId);
        try {
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException(accountId));

            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);

            LedgerEntry entry = new LedgerEntry();
            entry.setAccountId(accountId);
            entry.setAmount(amount);
            entry.setTransferRecord(null);
            entry.setCreatedAt(Instant.now());

            ledgerEntryRepository.save(entry);

            return new SimpleTransactionResult(entry.getId(), account.getBalance());
        } finally {
            accountLockService.unlock(accountId);
        }
    }
}
