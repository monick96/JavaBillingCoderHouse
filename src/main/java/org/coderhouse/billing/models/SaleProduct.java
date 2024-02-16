package org.coderhouse.billing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@RequiredArgsConstructor//generate constructor with only required arguments
@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
@Setter //generate all setters for all properties
//@ToString //to get a string representation of the object
@Table(name = "sale_product") //defines what the entity is called in the DB
public class SaleProduct {
    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Getter //only getter for id- field-level annotation takes priority
    private Integer id;

    @JsonIgnore
    @ManyToOne //many-to-one relationship with Product
    @JoinColumn(name ="product_id")
    @NonNull
    private Product product;

    @JsonIgnore
    @ManyToOne //many-to-one relationship with Product
    @JoinColumn(name ="sale_id")
    @NonNull
    private Sale sale;

    @NonNull //verify that the value is not null //im not sure about this property
    private Integer quantity;

}
