package ir.model.order.state;
import ir.model.order.Order;
import ir.model.order.OrderStatus;


public class CreatedState implements OrderState {

    @Override
    public void pay(Order order) {
        order.setState(new PaidState());
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("order not paid");
    }

    @Override
    public OrderStatus name() {
        return OrderStatus.CREATED;
    }
}
