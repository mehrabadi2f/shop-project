package ir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.dto.CheckoutRequest;
import ir.service.checkout.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@Tag(name = "Checkout API", description = "مدیریت checkout و پرداخت سفارش")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "شروع فرآیند checkout",
            description = """
                    فرآیند checkout را آغاز می‌کند.
                    
                    مراحل:
                    - اعتبارسنجی سبد خرید
                    - رزرو موجودی
                    - ساخت سفارش
                    - ایجاد پرداخت
                    - تایید پرداخت
                    """
    )
    public void checkout(@RequestBody CheckoutRequest request) {

        checkoutService.checkout(request.getUserId());

    }

}
