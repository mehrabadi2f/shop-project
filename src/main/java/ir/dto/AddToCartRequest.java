package ir.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class AddToCartRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;

}
