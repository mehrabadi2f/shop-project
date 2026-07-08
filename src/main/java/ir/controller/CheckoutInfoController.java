
package ir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.dto.CheckoutInfoRequest;
import ir.service.CheckoutInfoService;
import ir.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkoutInfo")
@RequiredArgsConstructor
@Tag(name = "Checkout API", description = "مدیریت checkoutفرم  و پرداخت سفارش")
public class CheckoutInfoController {

    private final CheckoutInfoService checkoutInfoService;
    private final CurrentUserService currentUserService;

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
    public void checkout(@PathVariable Long orderId ,
                         @RequestBody CheckoutInfoRequest request) {
long uid = currentUserService.getCurrentUser().getId();

      //  checkoutService.checkout(request.getUserId());
        checkoutInfoService.completeCheckoutInfo(orderId , uid , request);


    }

}
