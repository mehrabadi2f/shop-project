package ir.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "درخواست ایجاد قانون جدید")
public class RuleCreationRequest {

    @Schema(description = "نام نمایشی قانون", example = "قانون ویژه نوروزی")
    private String name;

    @Schema(description = "لیست اکشن‌های مجاز (مثلا: WITHDRAW, TRANSFER)", example = "[\"WITHDRAW\"]")
    private List<String> allowedActions;

    @Schema(description = "درصد مجاز برداشت", example = "70")
    private double withdrawPercentage;

    @Schema(description = "درصد مجاز انتقال", example = "50")
    private double transferPercentage;

    @Schema(description = "لیست شرایط قانون")
    private List<ConditionRequest> conditions;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getAllowedActions() { return allowedActions; }
    public void setAllowedActions(List<String> allowedActions) { this.allowedActions = allowedActions; }
    public double getWithdrawPercentage() { return withdrawPercentage; }
    public void setWithdrawPercentage(double withdrawPercentage) { this.withdrawPercentage = withdrawPercentage; }
    public double getTransferPercentage() { return transferPercentage; }
    public void setTransferPercentage(double transferPercentage) { this.transferPercentage = transferPercentage; }
    public List<ConditionRequest> getConditions() { return conditions; }
    public void setConditions(List<ConditionRequest> conditions) { this.conditions = conditions; }
}
