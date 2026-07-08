package ir.controller;

import ir.kimia.energyupload.dto.AccountResponse;
import ir.dto.ProductRequest;
import ir.model.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Products")
@Tag(name = "Product API")
public class ProductController {

    private final ProductService productService;
    //   private final IdempotencyService idempotencyService;

    public ProductController(ProductService prService) {
        this.productService = prService;
        //    this.idempotencyService = idempotencyService;
    }


    // ---------- ایجاد حساب جدید ----------
    @Operation(summary = "ایجاد محصول")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "محصول کالا ایجاد شد"))
    @PostMapping
    public ResponseEntity<ProductRequest> createProduct(
            @Parameter(description = "کلید یکتا برای جلوگیری از تکرار ایجاد حساب")
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ProductRequest request
    ) {

        ProductRequest p2 = new ProductRequest();
        // Account account = accountService.createAccount(request.getOwnerName(), request.getInitialBalance());

        //  AccountResponse response = new AccountResponse(account.getId(), account.getOwnerName(), account.getBalance());

        Long v;
        int v2 = 0;
        v = (long) v2;
        productService.createProduct(request.getName(), request.getDescription(), request.getPrice(), request.getStock(), request.getReservedStock(), request.getCategory(), v);
        return ResponseEntity.ok(request);
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> ps=productService.getAllProducts();
        return ResponseEntity.ok(ps);
    }


}


