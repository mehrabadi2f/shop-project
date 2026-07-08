package ir.service.imp;

import ir.model.Account;
import ir.exception.AccountNotFoundException;
import ir.model.user;
import ir.repository.AccountRepository;
import ir.repository.UserRepository;
import ir.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    /**
     * پیاده‌سازی ایجاد حساب جدید.
     * هر حساب با یک شناسه‌ی یکتا (UUID) ذخیره می‌شود.
     */
    @Override
    @Transactional
    public Account createAccount(String ownerName, BigDecimal initialBalance, Long userId) {
try {


    System.out.println("AccountService.createAccount called, userId = " + userId);

    if (accountRepository.existsByUserId(userId)) {
        throw new IllegalStateException("This user already has an account");
    }

    user userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new AccountNotFoundException("User not found with id=" + userId));

    String generatedId = "ACC-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();

    Account account = new Account(
            generatedId,
            ownerName,
            initialBalance == null ? BigDecimal.ZERO : initialBalance,
            userEntity
    );

    Account saved = accountRepository.save(account);
    System.out.println("Account saved successfully, id = " + saved.getId());

    return saved;
}
catch (Exception e) {
    e.printStackTrace();
    throw e;
}

    }
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
