package ir.controller;


import ir.dto.DepositRequest;
import ir.dto.DepositResponse;
import ir.service.DepositService;
import ir.service.IdempotencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class DepositController {

    private final DepositService depositService;
    private final IdempotencyService idempotencyService;

    public DepositController(DepositService depositService,
                             IdempotencyService idempotencyService) {
        this.depositService = depositService;
        this.idempotencyService = idempotencyService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(
            @RequestHeader("Idempotency-Key") String reqId,
            @Valid @RequestBody DepositRequest request
    ) {
        idempotencyService.checkAndSave(reqId);

        var result = depositService.deposit(
                request.getAccountId(),
                request.getAmount()
        );

        DepositResponse response = new DepositResponse(
                result.getTransactionId(),
                result.getNewBalance()
        );

        return ResponseEntity.ok(response);
    }
}

