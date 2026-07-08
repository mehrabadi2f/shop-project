
package ir.controller;

import ir.kimia.energyupload.dto.AccountResponse;
import ir.dto.CatRequest;
import ir.dto.CatResponse;
import ir.model.product.Category;
import ir.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Category")
@Tag(name = "Category API")
public class CategoryController {

    private final CategoryService categoryService;
 //   private final IdempotencyService idempotencyService;

    public CategoryController (CategoryService categoryService2) {
        this.categoryService = categoryService2;
    //    this.idempotencyService = idempotencyService;
    }

    // ---------- ایجاد حساب جدید ----------
    @Operation(summary = "ایجاد کتگوری")
    @PreAuthorize("hasRole('ADMIN')")
  //  @ApiResponses(@ApiResponse(responseCode = "200", description = "محصول کالا ایجاد شد"))
    @PostMapping
    public  ResponseEntity<CatResponse>  createCategory(
            @Parameter(description = "کلید یکتا برای جلوگیری از تکرار ایجاد کتگوری")
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CatRequest request
    ) {
       // Product
       // idempotencyService.checkAndSave(idempotencyKey);
       // Account account = accountService.createAccount(request.getOwnerName(), request.getInitialBalance());

      //  AccountResponse response = new AccountResponse(account.getId(), account.getOwnerName(), account.getBalance());
        Category category = new Category();
        category.setName(request.getName());
        categoryService.createCategory(request.getName());
        CatResponse response = new CatResponse();
        response.setName(category.getName());
       return ResponseEntity.ok(response);
    }

}
