package ir.service.checkout;
import ir.dto.event.UserActivityEvent;
import ir.model.analytics.ActivityType;
import ir.model.product.CartItem;
import ir.model.product.Product;
import ir.model.user;
import ir.repository.ProductRepository;
import ir.repository.UserRepository;

import ir.repository.CartItemRepository;


import ir.service.analytics.UserActivityPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ir.service.EventPublisher;
import org.springframework.transaction.reactive.TransactionalOperator;


import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserActivityPublisher userActivityPublisher;
    @Transactional
    public void addToCart(Long userId,
                          Long productId,
                          Integer quantity) {

        CartItem item = new CartItem();
        user user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

//        eventPublisher.publishProductSelected(userId, productId, quantity);

        UserActivityEvent event = new UserActivityEvent(
                userId,
                item.getProductId(),
                ActivityType.PURCHASE,
                "Checkout completed",
                LocalDateTime.now()
        );
        userActivityPublisher.publish(event);



        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);

        cartRepository.save(item);
    }

    public List<CartItem> getUserCart(Long userId) {

        return cartRepository.findByUserId(userId);
   }
@Transactional
    public void clearCart(Long userId) {
user user =userRepository.findById(userId).orElseThrow();
        cartRepository.deleteByUser(user);
 }

}
