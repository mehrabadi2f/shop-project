package ir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.service.RuleCreationService;
import ir.dto.RuleCreationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
@Tag(name = "مدیریت قوانین", description = "APIهای مربوط به ایجاد، ویرایش و مدیریت قوانین برداشت و انتقال")
public class RuleController {

    private final RuleCreationService ruleCreationService;

    public RuleController(RuleCreationService ruleCreationService) {
        this.ruleCreationService = ruleCreationService;
    }

    @PostMapping
    @Operation(
            summary = "ایجاد قانون جدید",
            description = "این متد برای ایجاد یک قانون جدید با شرایط دلخواه (AND, OR, XOR) استفاده می‌شود."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "قانون با موفقیت ایجاد شد"),
            @ApiResponse(responseCode = "400", description = "داده‌های ورودی نامعتبر هستند"),
            @ApiResponse(responseCode = "500", description = "خطای سرور")
    })
    public ResponseEntity<String> createRule(
            @Parameter(
                    description = "آبجکت شامل نام قانون، درصدها و لیست شرایط",
                    required = true
            )
            @RequestBody RuleCreationRequest request) {

        try {
            ruleCreationService.createRule(request);
            return ResponseEntity.ok("قانون با موفقیت ایجاد شد.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("خطا در ایجاد قانون: " + e.getMessage());
        }
    }
/*
    @GetMapping("/{id}")
    @Operation(
            summary = "دریافت جزئیات یک قانون",
            description = "بر اساس ID قانون، جزئیات کامل آن را برمی‌گرداند."
    )
   // public ResponseEntity<RuleResponse> getRuleById(@PathVariable Long id) {
        // فرض کن یک سرویس برای دریافت دیتا داری
        // RuleResponse rule = ruleService.findById(id);
        // return ResponseEntity.ok(rule);
        return ResponseEntity.ok(null); // برای نمونه
    }
    */

}
