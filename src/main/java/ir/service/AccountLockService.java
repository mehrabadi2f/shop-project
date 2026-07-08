package ir.service;


public interface AccountLockService {

    void lockInOrder(String accountId1, String accountId2);

    void unlock(String accountId1, String accountId2);

    void lock(String accountId);

    void unlock(String accountId);
}

