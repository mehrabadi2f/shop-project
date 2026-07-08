package ir.model.order.state;

import ir.model.order.Order;
import ir.model.order.OrderStatus;

public class stockReservedState implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("already paid");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("cannot cancel");
    }

    @Override
    public void ship(Order order) {
        order.setState(new ShippedState());
    }

    @Override
    public OrderStatus name() {
        return OrderStatus.STOCK_RESERVED;
    }
}