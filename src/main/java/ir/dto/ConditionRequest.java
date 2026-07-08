package ir.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "شرط واحد برای قانون")
public class ConditionRequest {

    @Schema(description = "نام فیلد در دیتابیس (مثلا: nationalCode, balance)", example = "nationalCode")
    private String fieldName;

    @Schema(description = "عملگر مقایسه (EQ, GT, LT, BETWEEN, IN)", example = "GT")
    private String operator;

    @Schema(description = "مقدار شرط", example = "100000")
    private String value;

    @Schema(description = "نوع رابطه با شرط قبلی (AND, OR, XOR)", example = "AND")
    private String relationType;

    // Getters and Setters
    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }
}
