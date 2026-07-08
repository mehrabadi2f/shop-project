package ir.model.order;
import ir.model.user;
import ir.model.order.state.OrderState;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;

    @Transient
    private OrderState currentState;

    @ManyToOne(fetch = FetchType.LAZY)
    private user user;

    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod PaymentMethod;
private String address;

    private BigDecimal price;
    public void pay() {
        currentState.pay(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    public void ship() {
        currentState.ship(this);
    }

    public void setState(OrderState state) {
        this.currentState = state;
        this.state = state.name().toString();
    }
    public  OrderState getState() {return currentState;}
public user getUser() {return user;}
    public void setUser(user referenceById) {
        this.user = referenceById;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.items = orderItems;
    }

    public void setCreatedAt(LocalDateTime now) {
        this.orderDate = now;
    }
    public enum PaymentMethod {
        WALLET,
        GATEWAY
    }

    public void setPaymentMethod(PaymentMethod PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }
    public String getAddress() {
        return address;
    }
public void setAddress(String address) {
        this.address = address;
}

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
public OrderStatus getStatus() {return status;}
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount=totalAmount;}
    public void recalculateTotalAmount() {
        this.totalAmount = this.items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    //  public void stockReserved() {

   //     if (status != OrderStatus.CREATED) {
     //       throw new RuntimeException();
       // }

       // status = OrderStatus.STOCK_RESERVED;
    //}

 //   public void waitingPayment() {

 //       status = OrderStatus.PAYMENT_PENDING;
  //  }





 //   public void failed() {

  //      status = OrderStatus.FAILED;
 //   }

}
