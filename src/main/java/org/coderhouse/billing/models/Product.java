package org.coderhouse.billing.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor//generate constructor with only required arguments
@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
@Setter //generate all setters for all properties
//@ToString //to get a string representation of the object
@Table(name = "products") //defines what the entity is called in the DB
public class Product {
    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Getter //only getter for id- field-level annotation takes priority
    private Integer id;

    @NonNull
    private String name;

    @NonNull //verify that the value is not null
    private Integer stock;

    @NonNull
    private Integer code;

    @NonNull
    private String description;

    @NonNull
    private Long price;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<SaleProduct> saleProducts = new HashSet<>();

    public void addSaleProduct(SaleProduct saleProduct){
        saleProduct.setProduct(this);
        saleProducts.add(saleProduct);
    }
}