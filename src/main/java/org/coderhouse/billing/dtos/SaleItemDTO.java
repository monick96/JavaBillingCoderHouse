package org.coderhouse.billing.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaleItemDTO {
    @Schema(description = "quantity of the proucts", required = true, example = "1")
    private Integer quantity;

    @Schema(description = "ID of the prouct", required = true, example = "5")
    private Integer productId;
}
