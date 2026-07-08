package ir.service.checkout.step;

import ir.model.order.Order;
import ir.model.order.OrderItem;
import ir.model.product.CartItem;
import ir.model.product.Product;
import ir.repository.CartItemRepository;
import ir.repository.OrderRepository;
import ir.repository.ProductRepository;
import ir.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    private  OrderRepository orderRepository;
    private  CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    @Transactional
    public Order createOrder(Long userId) {

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        Order order = new Order();
        order.setUser(userRepository.getReferenceById(userId));
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = cartItems.stream()
                .map(c -> {

                    Product product = productRepository.findById(c.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setQuantity(c.getQuantity());
                    item.setPrice(product.getPrice()); // قیمت لحظه خرید

                    return item;
                })
                .toList();

        order.setOrderItems(orderItems);

        orderRepository.save(order);

        cartItemRepository.deleteByUser(userRepository.getReferenceById(userId));

        return order;
    }

}
