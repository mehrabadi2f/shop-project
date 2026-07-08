package ir.service.imp;

import ir.service.AccountLockService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AccountLockServiceImpl implements AccountLockService {

    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    private ReentrantLock getLock(String accountId) {
        return locks.computeIfAbsent(accountId, id -> new ReentrantLock());
    }

    @Override
    public void lockInOrder(String accountId1, String accountId2) {
        if (accountId1.compareTo(accountId2) <= 0) {
            lock(accountId1);
            lock(accountId2);
        } else {
            lock(accountId2);
            lock(accountId1);
        }
    }

    @Override
    public void unlock(String accountId1, String accountId2) {
        unlock(accountId1);
        unlock(accountId2);
    }

    @Override
    public void lock(String accountId) {
        getLock(accountId).lock();
    }

    @Override
    public void unlock(String accountId) {
        getLock(accountId).unlock();
    }
}
