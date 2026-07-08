package ir.model.order.state;
import ir.model.order.Order;
import ir.model.order.OrderStatus;


public interface OrderState {


    void pay(Order order);

    void cancel(Order order);

    void ship(Order order);

    OrderStatus name();
}