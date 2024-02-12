package org.coderhouse.billing.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
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

    @Schema(description = "List of purchased products", required = true)
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


