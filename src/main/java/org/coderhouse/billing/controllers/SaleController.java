package org.coderhouse.billing.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.dtos.SaleRequestDTO;
import org.coderhouse.billing.models.Sale;
import org.coderhouse.billing.services.ClientService;
import org.coderhouse.billing.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SaleController {
    @Autowired
    SaleService saleService;

//    @Autowired
//    ClientService clientService;



    //obtain all salesReceiptDTO
    @Operation(
            summary = "obtain all salesReceiptDTO",
            description = "Returns a list of all available sales receipts in DTO format.",
            operationId = "getSaleReceipts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = SaleReceiptDTO.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/salesReceipts")
    public List<SaleReceiptDTO> getSalesReceiptDTO(){

       return saleService.getSalesReceiptsDTO();

    }

    //create a postMapping method for createsale
    @PostMapping("/salesReceipts")
    public ResponseEntity<Object> createSale(@RequestBody SaleRequestDTO saleRequestDTO){

        return saleService.createSale(saleRequestDTO);



    }

}


