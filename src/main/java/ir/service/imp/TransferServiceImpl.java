package ir.service.imp;

import ir.model.Account;
import ir.model.LedgerEntry;
import ir.model.TransferRecord;
import ir.exception.AccountNotFoundException;
import ir.exception.InsufficientBalanceException;
import ir.repository.AccountRepository;
import ir.repository.LedgerEntryRepository;
import ir.repository.TransferRecordRepository;
import ir.security.UserContextHolder;
import ir.service.RedisLockService;
import ir.service.TransferService;
import ir.service.model.TransferResult;
import ir.service.model.TransferStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private static final Duration LOCK_TTL = Duration.ofSeconds(10);

    private final AccountRepository accountRepository;
    private final TransferRecordRepository transferRecordRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final RedisLockService redisLockService;

    public TransferServiceImpl(AccountRepository accountRepository,
                               TransferRecordRepository transferRecordRepository,
                               LedgerEntryRepository ledgerEntryRepository,
                               RedisLockService redisLockService) {
        this.accountRepository = accountRepository;
        this.transferRecordRepository = transferRecordRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.redisLockService = redisLockService;
    }

    @Override
    @Transactional
    public TransferResult transfer(String fromAccountId,
                                   String toAccountId,
                                   BigDecimal amount) {

        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("fromAccountId and toAccountId cannot be the same");
        }
        String userId = UserContextHolder.getUserId();

      System.out.println ("transfer requested by user {}"+ userId);

        // ✅ 1) جلوگیری از deadlock: sort account ids
        List<String> orderedAccounts = Arrays
                .asList(fromAccountId, toAccountId)
                .stream()
                .sorted()
                .toList();

        String firstLockKey  = "lock:account:" + orderedAccounts.get(0);
        String secondLockKey = "lock:account:" + orderedAccounts.get(1);

        String firstToken = null;
        String secondToken = null;

        try {
            // ✅ 2) گرفتن lock اول
            firstToken = redisLockService.lock(firstLockKey, LOCK_TTL);
            if (firstToken == null) {
                throw new IllegalStateException("Account is currently locked: " + orderedAccounts.get(0));
            }

            // ✅ 3) گرفتن lock دوم
            secondToken = redisLockService.lock(secondLockKey, LOCK_TTL);
            if (secondToken == null) {
                throw new IllegalStateException("Account is currently locked: " + orderedAccounts.get(1));
            }

            // ✅ 4) Load accounts
            Account from = accountRepository.findById(fromAccountId)
                    .orElseThrow(() -> new AccountNotFoundException(fromAccountId));

            Account to = accountRepository.findById(toAccountId)
                    .orElseThrow(() -> new AccountNotFoundException(toAccountId));

            // ✅ 5) Business validation
            if (from.getBalance().compareTo(amount) < 0) {
                throw new InsufficientBalanceException(fromAccountId);
            }

            // ✅ 6) Update balances
            from.setBalance(from.getBalance().subtract(amount));
            to.setBalance(to.getBalance().add(amount));

            // ✅ 7) Create transfer record
            TransferRecord record = new TransferRecord();
            record.setFromAccountId(fromAccountId);
            record.setToAccountId(toAccountId);
            record.setAmount(amount);
            record.setStatus(TransferStatus.PENDING);
            record.setCreatedAt(Instant.now());
            transferRecordRepository.save(record);

            // ✅ 8) Ledger entries
            LedgerEntry debit = new LedgerEntry();
            debit.setAccountId(fromAccountId);
            debit.setAmount(amount.negate());
            debit.setTransferRecord(record);
            debit.setCreatedAt(Instant.now());

            LedgerEntry credit = new LedgerEntry();
            credit.setAccountId(toAccountId);
            credit.setAmount(amount);
            credit.setTransferRecord(record);
            credit.setCreatedAt(Instant.now());

            ledgerEntryRepository.save(debit);
            ledgerEntryRepository.save(credit);

            // ✅ 9) Persist accounts
            accountRepository.save(from);
            accountRepository.save(to);

            // ✅ 10) Mark completed
            record.setStatus(TransferStatus.COMPLETED);
            transferRecordRepository.save(record);

            return new TransferResult(record.getId(), record.getStatus());

        } finally {
            // ✅ 11) unlock safely (reverse order is fine)
            if (secondToken != null) {
                redisLockService.unlock(secondLockKey, secondToken);
            }
            if (firstToken != null) {
                redisLockService.unlock(firstLockKey, firstToken);
            }
        }
    }
}
