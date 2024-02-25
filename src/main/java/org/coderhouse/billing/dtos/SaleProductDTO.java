package org.coderhouse.billing.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.coderhouse.billing.models.SaleProduct;

@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
public class SaleProductDTO {
    @NonNull
    @Schema(description = "Name of the product", required = true, example = "Apple iPhone 15")
    private String productName;

    @NonNull
    @Schema(description = "Description of the product", required = true, example = "RAM:(128 Gb)- color: Negro")
    private String productDescription;


    @Schema(description = "Price of the product", required = true, example = "1300000")
    private Long price;

    @NonNull
    @Schema(description = "purchased quantity of the product", required = true, example = "1")
    private Integer quantity;

    public SaleProductDTO(SaleProduct saleProduct) {

        this.productName = saleProduct.getProduct().getName();

        this.productDescription = saleProduct.getProduct().getDescription();

        this.quantity = saleProduct.getQuantity();

        this.price = saleProduct.getPrice();

    }




}
