package ir.controller;

import ir.dto.TransferRequest;
import ir.dto.TransferResponse;
import ir.service.TransferService;
import ir.service.IdempotencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TransferController {

    private final TransferService transferService;
    private final IdempotencyService idempotencyService;

    public TransferController(TransferService transferService,
                              IdempotencyService idempotencyService) {
        this.transferService = transferService;
        this.idempotencyService = idempotencyService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(
            @RequestHeader("Idempotency-Key") String reqId,
            @Valid @RequestBody TransferRequest request
    ) {
        // 1) چک و ذخیره Idempotency Key
        idempotencyService.checkAndSave(reqId);

        // 2) انجام انتقال
        var result = transferService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );

        // 3) ساخت Response
        TransferResponse response = new TransferResponse(
                result.getTransferId(),
                result.getStatus().name()
        );

        return ResponseEntity.ok(response);
    }
}
