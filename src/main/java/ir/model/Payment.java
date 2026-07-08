package ir.model;

import ir.model.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

/// /
///
   @OneToOne(optional = false) // یعنی هر Account باید user داشته باشد (در سطح object)
     @JoinColumn(name = "orser_id", unique = true, nullable = false)
     private Order order;
///
///
    @OneToOne(optional = false) // یعنی هر Account باید user داشته باشد (در سطح object)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private user user;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String gateway;

    private String referenceId;
    private String authority;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {return paymentMethod;}

    public user getUser() {
        return user;
    }
    public void setUser(user user) { this.user = user; }

    public enum PaymentMethod {
        WALLET,
        GATEWAY
    }

    public enum PaymentStatus {

        PENDING,
        PROCESSING,
        SUCCESS,
        FAILED,
        CANCELED,
        REFUNDED

    }

}