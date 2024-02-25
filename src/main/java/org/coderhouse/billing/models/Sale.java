package org.coderhouse.billing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Boolean isActive;


    @ManyToOne //many-to-one relationship with SaleProduct
    @JoinColumn(name ="client_id")
    private Client client;


    @OneToMany (mappedBy="sale", fetch=FetchType.LAZY) //one-to-may relationship with SaleProduct
    private Set <SaleProduct> saleProducts = new HashSet<>();


    public void addSaleProduct(SaleProduct saleProduct){
        saleProduct.setSale(this);
        saleProducts.add(saleProduct);
    }


    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Client: ").append(client.getName()).append(" ").append(client.getLastName()).append("\n");
        result.append("Total purchase: $").append(calculateTotal()).append("\n");
        result.append("Products:\n");

        for (SaleProduct saleProduct : saleProducts) {
            result.append("  - ").append(saleProduct.getProduct().getName()).append(": ");
            result.append("Quantity: ").append(saleProduct.getQuantity()).append(", ");
            result.append("Unit price: ").append(saleProduct.getProduct().getPrice()).append("\n");
        }

        return result.toString();
    }

    public Long calculateTotal() {
        this.total = 0L;
        for (SaleProduct saleProduct : saleProducts) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice();
        }
        return total;
    }


}
