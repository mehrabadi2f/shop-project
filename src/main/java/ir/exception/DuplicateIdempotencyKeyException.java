package ir.exception;

public class DuplicateIdempotencyKeyException extends RuntimeException {
    public DuplicateIdempotencyKeyException(String requestId) {
        super("Duplicate Idempotency-Key: " + requestId);
    }}
