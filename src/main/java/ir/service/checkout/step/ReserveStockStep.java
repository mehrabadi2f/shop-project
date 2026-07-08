package ir.service.checkout.step;

import ir.model.order.OrderItem;
import ir.model.product.Product;
import ir.repository.ProductRepository;
import ir.service.checkout.CheckoutContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReserveStockStep implements CheckoutSaga {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void execute(CheckoutContext context) {

        for (OrderItem item : context.getItems()) {

            Product product = productRepository
                    .findById(item.getProductId())
                    .orElseThrow();

            product.reserve(item.getQuantity());

            productRepository.save(product);
        }

    }

    @Override
    @Transactional
    public void compensate(CheckoutContext context) {

        for (OrderItem item : context.getItems()) {

            Product product = productRepository
                    .findById(item.getProductId())
                    .orElseThrow();

            product.releaseReservation(item.getQuantity());

            productRepository.save(product);
        }

    }
}
