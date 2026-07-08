package ir.service.checkout;

import ir.model.order.Order;
import ir.model.order.OrderItem;
import ir.model.product.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutContext {

    private String userId;

    private List<CartItem> cartItems;
    List<OrderItem> orderItems;

    private BigDecimal totalAmount;

    private Order order;

    public String getUserId() {
        return userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public List<CartItem> getcItems()
    {
        return cartItems;
    }
    public Order getOrder() {return order;}

    public List<OrderItem> getItems()
    {
        return orderItems;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public void setItems(List<OrderItem> orders) {
        this.orderItems = orders;
    }
    public  void setUserId(long userId) {
        this.userId = Long.toString(userId);
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
