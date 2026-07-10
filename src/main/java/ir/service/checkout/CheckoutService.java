package ir.service.checkout;
import ir.dto.event.UserActivityEvent;
import ir.model.analytics.ActivityType;
import ir.model.order.OrderItem;
import ir.model.product.CartItem;
import ir.repository.ProductRepository;
import ir.service.EventPublisher;

import ir.service.analytics.UserActivityPublisher;
import lombok.RequiredArgsConstructor;
//صفحه
       // 403 Access Denied
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CheckoutService {

  private final CartService cartService;
  private final CheckoutsSaga checkoutSaga;
  private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;
    private final UserActivityPublisher userActivityPublisher;

    public void checkout(Long userId) {

        List<CartItem> cartItems =
            cartService.getUserCart(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = cartItems.stream()
                .map(c -> {
                    OrderItem item = new OrderItem();
                    item.setProduct(productRepository.getReferenceById(c.getProductId()));
                    item.setQuantity(c.getQuantity());
                    return item;
                })
                .toList();




        cartItems.forEach(item -> {
            UserActivityEvent event = new UserActivityEvent(
                    userId,
                    item.getProductId(),
                    ActivityType.PURCHASE,
                    "Checkout completed",
                    LocalDateTime.now()
            );
            userActivityPublisher.publish(event);
        });



       // cartItems.forEach(item ->
         //       UserActivityPublisher.publish(new UserActivityEvent(userId, matchedProductId, ActivityType.SEARCH, query, LocalDateTime.now()));

        // UserActivityPublisher.publish(userId, item.getProductId(), item.getQuantity())
        //);
        CheckoutContext context = new CheckoutContext();
        context.setCartItems(cartItems);
       context.setUserId(userId);
         context.setItems(orderItems);

        checkoutSaga.execute(context);

        cartService.clearCart(userId);

    }

}
