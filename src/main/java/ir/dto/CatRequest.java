package ir.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "درخواست ایجاد کتگوری")
@Data
public class CatRequest {
    @NotBlank
    @Schema(description = "اسم کتگوری", example = "بهداشتی", requiredMode = Schema.RequiredMode.REQUIRED)
    String name;
}
