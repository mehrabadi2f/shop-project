package ir.service;

import ir.dto.CheckoutInfoRequest;
import ir.model.order.Order;
import ir.model.order.OrderStatus;
import ir.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutInfoService {

    private final OrderRepository orderRepository;

    @Transactional
    public void completeCheckoutInfo(Long orderId, Long userId, CheckoutInfoRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        if (order.getState().name() != OrderStatus.AWAITING_USER_INFO) {
            throw new RuntimeException("Order is not in AWAITING_USER_INFO state");
        }

       // order.setAddressId(request.getAddressId());
        order.setAddress(request.getAddress());
        order.setPaymentMethod(request.getPaymentMethod());
       // order.setState

        orderRepository.save(order);
    }
}
