package org.coderhouse.billing.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.coderhouse.billing.models.Sale;
import org.coderhouse.billing.models.SaleProduct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
public class SaleReceiptDTO {

    @NonNull
    private String clientName;

    @NonNull
    private LocalDateTime date;

    @NonNull
    private Long totalPurchase;

    private List<SaleProductDTO> saleProducts = new ArrayList<>();

    public SaleReceiptDTO(Sale sale){

        this.clientName = sale.getClient().getName() + " " + sale.getClient().getLastName();
        
        this.date = sale.getDate();

        for (SaleProduct saleProduct: sale.getSaleProducts()){

            SaleProductDTO saleProductDTO = new SaleProductDTO(saleProduct);

            this.saleProducts.add(saleProductDTO);
        }

        this.totalPurchase = sale.getTotal();


    }

}
