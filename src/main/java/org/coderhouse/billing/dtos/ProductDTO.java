package org.coderhouse.billing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.coderhouse.billing.models.Product;

@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
public class ProductDTO {

    @NonNull
    private String name;

    @NonNull
    private Integer stock;

    @NonNull
    private String description;

    @NonNull
    private Long price;

    public ProductDTO(Product product){

        this.name = product.getName();

        this.stock = product.getStock();

        this.description = product.getDescription();

        this.price = product.getPrice();

    }


}
