package ir.service.payment;

import ir.kimia.energyupload.dto.PaymentRequest;
import ir.kimia.energyupload.dto.PaymentResponse;
import ir.kimia.energyupload.dto.PaymentVerification;
import ir.model.Payment;
import ir.model.order.Order;
import ir.repository.AccountRepository;
import ir.repository.OrderRepository;
import ir.repository.PaymentRepository;
import ir.service.AccountService;
import ir.service.CurrentUserService;
import ir.service.WithdrawService;
import org.springframework.stereotype.Service;
import ir.model.order.OrderStatus;

import java.math.BigDecimal;
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private  final OrderRepository orderRepository;
    private final PaymentGateway gateway;
    private final  CurrentUserService currentUserService;
    private final WithdrawService withdrawService;
    private  AccountService accountService;
    private AccountRepository accountRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          FakeGateway gateway , OrderRepository orderRepository2 , CurrentUserService currentUserService2
    , WithdrawService withdrawService2) {

        this.paymentRepository = paymentRepository;
        this.gateway = gateway;
        this.orderRepository = orderRepository2;
        this.withdrawService = withdrawService2;
        this.currentUserService = currentUserService2;
    }

    public PaymentResponse createPayment(Long orderId, BigDecimal amount) {

        Payment payment = new Payment();
        orderRepository.findById(orderId).ifPresent(payment::setOrder);

        payment.setAmount(amount);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setGateway("FAKE");

        paymentRepository.save(payment);

        PaymentRequest request = new PaymentRequest(
                payment.getId(),
                amount,
                "http://localhost:8081/payments/callback"
        );
        PaymentResponse response = gateway.requestPayment(request);

        // اینجا authority ذخیره می‌شود
        payment.setAuthority(response.authority());

        paymentRepository.save(payment);

        return response;


    }

    public Payment verifyPayment(String authority) {
        Payment payment;

        Payment payment2

          = (Payment) paymentRepository.findByAuthorityForUpdate(authority)
                .orElseThrow();

        // اگر قبلاً نهایی شده، دوباره پردازش نکن
        if (payment2.getStatus() == Payment.PaymentStatus.SUCCESS) {
            return payment2;
        }
        PaymentVerification verification =
                gateway.verify(authority);

         payment =
                paymentRepository.findByAuthority(authority)
                        .orElseThrow();

        if (verification.success()) {

            payment.setStatus(Payment.PaymentStatus.SUCCESS);
            payment.setReferenceId(verification.referenceId());

        } else {

            payment.setStatus(Payment.PaymentStatus.FAILED);
        }

        return paymentRepository.save(payment);
    }

    public PaymentResponse createPayment2(Long orderId) {

          Long userId= currentUserService.getCurrentUser().getId();
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId))
            throw new RuntimeException("Access denied");

        if (order.getState().name() != OrderStatus.PAYMENT_PENDING)
            throw new RuntimeException("Order not ready. Complete address/payment method first.");
order.recalculateTotalAmount();
        var amount = order.getTotalAmount(); // یا از orderItems حساب کن

        if (order.getPaymentMethod() == Order.PaymentMethod.WALLET) {
            var acc = accountRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

           // acc.debit(amount);
            withdrawService.withdraw(acc.getId(), amount);
            // Payment record
            var payment = new Payment();



            payment.setOrder(order);
            payment.setUser(order.getUser());
            payment.setAmount(amount);
            payment.setPaymentMethod(Payment.PaymentMethod.WALLET);
            payment.setStatus(Payment.PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            order.setStatus(OrderStatus.PAID);

            return new PaymentResponse(null, "WALLET-PAID");
        }

        // GATEWAY
        var payment = new Payment();
        payment.setOrder(order);
        payment.setUser(order.getUser());
        payment.setAmount(amount);
        payment.setPaymentMethod(Payment.PaymentMethod.GATEWAY);
        payment.setStatus(Payment.PaymentStatus.PENDING);

        payment.setGateway("FAKE");

        paymentRepository.save(payment);

        PaymentRequest request = new PaymentRequest(
                payment.getId(),
                amount,
                "http://localhost:8081/payments/callback"
        );
        PaymentResponse response = gateway.requestPayment(request);

        // اینجا authority ذخیره می‌شود
        payment.setAuthority(response.authority());

        paymentRepository.save(payment);

        return response;
    }
}



