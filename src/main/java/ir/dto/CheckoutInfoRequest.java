package ir.dto;

import ir.model.order.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutInfoRequest {
    private String address;
    private Order.PaymentMethod paymentMethod;
}
