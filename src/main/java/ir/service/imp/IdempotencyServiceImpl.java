package ir.service.imp;

import java.time.Duration;
import java.time.Instant;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ir.model.IdempotentRequest;
import ir.exception.DuplicateIdempotencyKeyException;
import ir.repository.IdempotentRequestRepository;
import ir.service.IdempotencyService;

@Service
public class IdempotencyServiceImpl implements IdempotencyService {

    private final IdempotentRequestRepository repository;
    private final StringRedisTemplate redisTemplate;

    // یک TTL کوتاه برای Redis - مثلا 10 دقیقه کافی است
    private static final Duration IDEMPOTENCY_TTL = Duration.ofMinutes(10);
    private static final String PREFIX = "idem:";

    public IdempotencyServiceImpl(IdempotentRequestRepository repository,
                                  StringRedisTemplate redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public void checkAndSave(String requestId) {

        String redisKey = PREFIX + requestId;

        // 1️⃣ بررسی در Redis
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(redisKey, "USED", IDEMPOTENCY_TTL);

        if (!Boolean.TRUE.equals(success)) {
            throw new DuplicateIdempotencyKeyException(requestId);
        }

        // 2️⃣ همچنین ثبت دائم در جدول دیتابیس برای بررسی‌های بعدی یا گزارش
        if (repository.existsById(requestId)) {
            // در صورت همزمانی احتمالی: رول‌بک کنیم و ادعا کنیم تکراری است
            redisTemplate.delete(redisKey);
            throw new DuplicateIdempotencyKeyException(requestId);
        }

        IdempotentRequest req = new IdempotentRequest();
        req.setId(requestId);
        req.setCreatedAt(Instant.now());
        repository.save(req);
    }
}
