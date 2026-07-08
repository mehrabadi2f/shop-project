package ir.repository;

import ir.model.product.CartItem;
import ir.model.user;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(user user);
    List<CartItem> findByUserId(Long userId);
    void deleteByUser(user user);


}
