package ir.repository;


import ir.model.TransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRecordRepository extends JpaRepository<TransferRecord, Long> {

    // پیدا کردن انتقال با کلید idempotency
    Optional<TransferRecord> findById(Long id);

    // گرفتن همه انتقال‌های مربوط به یک حساب
    List<TransferRecord> findByFromAccountIdOrToAccountId(String fromAccountId, String toAccountId);
}

