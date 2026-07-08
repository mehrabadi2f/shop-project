package ir.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "درخواست ایجاد کالا")
@Data
public class ProductRequest {
    @NotBlank
    @Schema(description = "اسم کالا", example = "کرم مرطوب کننده", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "توضیح کالا", example = "کرم مرطوب کننده")

    private String description;

    @Schema(description = "قیمت", example = "150000")
    private BigDecimal price;

    @Schema(description = "تعداد کالا", example = "5")
    private Integer stock;
    @Schema(description = "تعداد رزرو شده ها", example = "2")
    private Integer reservedStock;

    @Schema(description = "کتگوری", example = "بهداشت")
    private String category;


}



