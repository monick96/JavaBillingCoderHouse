package org.coderhouse.billing.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SaleController {
    @Autowired
    SaleService saleService;



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

}


