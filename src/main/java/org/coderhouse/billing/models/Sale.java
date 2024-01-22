package org.coderhouse.billing.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor//generate constructor with only required arguments
@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
@Setter //generate all setters for all properties
@ToString //to get a string representation of the object
@Table(name = "sale") //defines what the entity is called in the DB
public class Sale {
    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Getter //only getter for id- field-level annotation takes priority
    private Integer id;

    @NonNull //verify that the value is not null
    private Integer amount;

    @NonNull
    private LocalDateTime date;

    private Long total;

    @ManyToOne //many-to-one relationship with SaleProduct
    @JoinColumn(name ="client_id")
    private Client client;

    @OneToMany (mappedBy="sale", fetch=FetchType.EAGER) //one-to-may relationship with SaleProduct
    private Set <SaleProduct> saleProducts = new HashSet<>();

    public void addSaleProduct(SaleProduct saleProduct){
        saleProduct.setSale(this);
        saleProducts.add(saleProduct);
    }

}
