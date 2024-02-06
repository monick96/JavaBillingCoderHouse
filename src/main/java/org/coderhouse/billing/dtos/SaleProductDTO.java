package org.coderhouse.billing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.coderhouse.billing.models.SaleProduct;

@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
public class SaleProductDTO {
    @NonNull
    private String productName;

    @NonNull
    private String productDescription;

    @NonNull
    private Long price;

    @NonNull
    private Integer quantity;

    public SaleProductDTO(SaleProduct saleProduct) {

        this.productName = saleProduct.getProduct().getName();

        this.productDescription = saleProduct.getProduct().getDescription();

        this.quantity = saleProduct.getQuantity();

        this.price = saleProduct.getProduct().getPrice();

    }




}
