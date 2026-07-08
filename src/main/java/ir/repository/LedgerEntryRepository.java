package ir.repository;


import ir.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {

    // گرفتن همه ورودی‌های دفتر کل یک حساب
    List<LedgerEntry> findByAccountId(String accountId);

    // گرفتن ورودی‌ها بر اساس شناسه تراکنش
    List<LedgerEntry> findByTransferRecordId(Long transferRecordId);
}
