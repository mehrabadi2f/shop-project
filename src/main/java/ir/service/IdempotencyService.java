package ir.service;

public interface IdempotencyService {
    void checkAndSave(String requestId);
}

