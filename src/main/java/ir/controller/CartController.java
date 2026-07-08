package ir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import ir.kimia.energyupload.dto.AddToCartRequest;
//import ir.kimia.energyupload.service.cart.CartService;
import ir.dto.AddToCartRequest;
import ir.service.checkout.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart API", description = "مدیریت سبد خرید")
public class CartController {

   private final CartService cartService;

    @PostMapping("/add")
   @ResponseStatus(HttpStatus.OK)
   @Operation(
           summary = "اضافه کردن کالا به سبد خرید",
            description = "کاربر یک کالا را با تعداد مشخص به سبد خرید اضافه می‌کند"
    )
    public void addToCart(@Valid @RequestBody  AddToCartRequest request) {

        cartService.addToCart(
                request.getUserId(),
              request.getProductId(),
                request.getQuantity()
        );

    }

}
