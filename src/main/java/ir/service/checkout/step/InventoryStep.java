package ir.service.checkout.step;

import ir.model.product.CartItem;
import ir.model.product.Product;
import ir.service.checkout.CheckoutContext;
import org.springframework.stereotype.Component;

@Component
public class InventoryStep implements CheckoutStep {

    @Override
    public void process(CheckoutContext context) {

        for (CartItem item : context.getCartItems()) {

            Product product = item.getProduct();

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("stock insufficient");
            }
        }
    }
}
