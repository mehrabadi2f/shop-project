package ir.service.checkout.step;

import ir.model.order.Order;
import ir.model.order.OrderItem;
import ir.model.product.Product;
import ir.repository.OrderRepository;
import ir.repository.ProductRepository;
import ir.repository.UserRepository;
import ir.service.checkout.CheckoutContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateOrderStep implements CheckoutSaga {

    private final OrderRepository orderRepository;
    private  CheckoutContext checkoutContext;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void execute(CheckoutContext context) {

        Order order = new Order();

        order.setUser(userRepository.getReferenceById(Long.valueOf(context.getUserId())));
        order.setCreatedAt(LocalDateTime.now());


        List<OrderItem> orderItems =context.getCartItems().stream()
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

        //Order order = Order.create(
        //        context.getUserId(),
          //      context.getItems()
        //);

        //order.stockReserved();
        //order.waitingPayment();

       orderRepository.save(order);

        context.setOrder(order);
    }

    @Override
    public void compensate(CheckoutContext context) {

        Order order = context.getOrder();

        if (order != null) {

            order.cancel();

            orderRepository.save(order);
        }
    }
}
