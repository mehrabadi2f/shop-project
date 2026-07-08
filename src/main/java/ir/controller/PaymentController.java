package ir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.kimia.energyupload.dto.PaymentResponse;
import ir.service.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment API")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }



    @Operation(summary = "Create/Start payment")
    @PostMapping
    public PaymentResponse createPayment(@RequestParam Long orderId) {
        //Long userId = 1L; // TODO: از لاگین
        return paymentService.createPayment2(orderId);
    }
    /*
    @Operation(summary = "Create payment")
    @PostMapping
    public PaymentResponse createPayment(
            @RequestParam Long orderId,
            @RequestParam BigDecimal amount
    ) {

        return paymentService.createPayment(orderId, amount);
    }
*/
    @Operation(summary = "Gateway callback")
    @GetMapping("/callback")
    public ResponseEntity<String> callback(
            @RequestParam String authority
    ) {

        paymentService.verifyPayment(authority);

        return ResponseEntity.ok("Payment verified");
    }
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(
            @RequestParam String authority) {

        paymentService.verifyPayment(authority);

        return ResponseEntity.ok("Webhook processed");
    }

}
