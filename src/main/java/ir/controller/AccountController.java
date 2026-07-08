package ir.controller;

import ir.kimia.energyupload.dto.AccountResponse;
import ir.dto.CreateAccountRequest;
import ir.model.Account;
import ir.service.AccountService;
import ir.service.IdempotencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Account API", description = "مدیریت حساب‌های بانکی (ایجاد، مشاهده، لیست همه حساب‌ها)")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final IdempotencyService idempotencyService;

    public AccountController(AccountService accountService, IdempotencyService idempotencyService) {
        this.accountService = accountService;
        this.idempotencyService = idempotencyService;
    }

    // ---------- ایجاد حساب جدید ----------
    @Operation(summary = "ایجاد حساب جدید برای کاربر")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "حساب ایجاد شد"))
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Parameter(description = "کلید یکتا برای جلوگیری از تکرار ایجاد حساب")
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreateAccountRequest request
    ) {
        System.out.println("hesab...............................................................");
        idempotencyService.checkAndSave(idempotencyKey);
        System.out.println("hesab...............................................................");

        Account account = accountService.createAccount(request.getOwnerName(), request.getInitialBalance(),request.getUserid());

        AccountResponse response = new AccountResponse(account.getId(), account.getOwnerName(), account.getBalance());
        return ResponseEntity.ok(response);
    }

    // ---------- دریافت اطلاعات حساب خاص ----------
    @Operation(summary = "دریافت جزئیات حساب با شناسه حساب")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "جزئیات حساب برگردانده شد"),
            @ApiResponse(responseCode = "404", description = "حسابی با این شناسه یافت نشد", content = @Content)
    })
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(
            @Parameter(description = "شناسه حساب", example = "ACC-12345678")
            @PathVariable String accountId
    ) {
        Account account = accountService.getAccountById(accountId);
        AccountResponse response = new AccountResponse(account.getId(), account.getOwnerName(), account.getBalance());
        return ResponseEntity.ok(response);
    }

    // ---------- لیست همه حساب‌ها ----------
    @Operation(summary = "گرفتن لیست همه حساب‌ها در سیستم")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "لیست حساب‌ها"))
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> responses = accountService.getAllAccounts().stream()
                .map(acc -> new AccountResponse(acc.getId(), acc.getOwnerName(), acc.getBalance()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
