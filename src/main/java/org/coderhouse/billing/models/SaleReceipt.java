package org.coderhouse.billing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.coderhouse.billing.dtos.SaleProductDTO;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor//generate constructor with only required arguments
@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
@Setter //generate all setters for all properties
//@ToString //to get a string representation of the object
@Table(name = "Sale_Receipt") //defines what the entity is called in the DB
public class SaleReceipt {
    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Getter //only getter for id- field-level annotation takes priority
    private Integer id;

    @NonNull
    @Schema(description = "Name of the client", required = true, example = "Juan Perez")
    private String clientName;

    @NonNull
    @Schema(description = "Date and time of the sale", required = true, example = "2023-11-14T18:23:56.789Z")
    private LocalDateTime date;

    @Setter
    @Schema(description = "total amount of products purchased", required = true, example = "1")
    private int totalProductQuantity;

    @NonNull
    @Schema(description = "Total amount of the sale", required = true,example = "1300000")
    private Long totalPurchase;


    @OneToMany(mappedBy = "saleReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "List of purchased products", required = true)
    private List<SaleProduct> saleProducts = new ArrayList<>();






//    public SaleReceipt(SaleReceiptDTO saleReceiptDTO){
//
//        this.clientName = saleReceiptDTO.getClientName();
//
//        this.date = saleReceiptDTO.getDate();
//
//        this.totalProductQuantity = saleReceiptDTO.getTotalProductQuantity();
//
//        this.totalPurchase = saleReceiptDTO.getTotalPurchase();
//
//        for (SaleProductDTO saleProductDTO: saleReceiptDTO.getSaleProducts()){
//
//            SaleProduct saleProduct = new SaleProduct(saleProductDTO);
//
//            this.saleProducts.add(saleProduct);
//        }
//
//
//    }
    public SaleReceipt(Sale sale) {

        this.clientName = sale.getClient().getName() + " " + sale.getClient().getLastName();

        this.date = sale.getDate();

        this.saleProducts.addAll(sale.getSaleProducts());

        this.totalPurchase = sale.calculateTotal();

    }

    public void addSaleProduct(SaleProduct saleProduct) {
        saleProducts.add(saleProduct);
        saleProduct.setSaleReceipt(this);
    }


}
