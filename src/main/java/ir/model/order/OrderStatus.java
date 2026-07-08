package ir.model.order;


public enum OrderStatus {
    CREATED,
    STOCK_RESERVED,
    AWAITING_USER_INFO,
    PAYMENT_PENDING,
    PAID,
    Shipped,
    CANCELLED,
    FAILED
}