package ir.controller;

import ir.dto.WithdrawRequest;
import ir.dto.WithdrawResponse;
import ir.service.WithdrawService;
import ir.service.IdempotencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Withdraw API", description = "عملیات برداشت وجه از حساب با پشتیبانی از Idempotency-Key برای جلوگیری از تراکنش تکراری")
@RestController
@RequestMapping("/api")
public class WithdrawController {

    private final WithdrawService withdrawService;
    private final IdempotencyService idempotencyService;

    public WithdrawController(WithdrawService withdrawService,
                              IdempotencyService idempotencyService) {
        this.withdrawService = withdrawService;
        this.idempotencyService = idempotencyService;
    }

    @Operation(
            summary = "برداشت وجه از حساب مشخص",
            description = """
                    این متد وجه موردنظر را از حساب مشتری برداشت می‌کند.
                    برای جلوگیری از برداشت تکراری، لازم است هدر Idempotency-Key در هر درخواست ارسال شود.
                    در صورت تکرار همان کلید، تراکنش مجدد انجام نخواهد شد.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "برداشت با موفقیت انجام شد",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WithdrawResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "transactionId": 902145,
                                      "newBalance": 450000.50
                                    }
                                    """)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "درخواست نامعتبر یا موجودی ناکافی", content = @Content),
            @ApiResponse(responseCode = "404", description = "حساب موردنظر یافت نشد", content = @Content)
    })
    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(
            @Parameter(description = "کلید یکتا برای جلوگیری از تکرار تراکنش", example = "REQ-20240507-WD-001")
            @RequestHeader("Idempotency-Key") String reqId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "درخواست برداشت شامل شناسه حساب و مبلغ موردنظر",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = WithdrawRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "accountId": "ACC-1001",
                                      "amount": 100000.00
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody WithdrawRequest request
    ) {
        idempotencyService.checkAndSave(reqId);

        var result = withdrawService.withdraw(
                request.getAccountId(),
                request.getAmount()
        );

        WithdrawResponse response = new WithdrawResponse(
                result.getTransactionId(),
                result.getNewBalance()
        );

        return ResponseEntity.ok(response);
    }
}
