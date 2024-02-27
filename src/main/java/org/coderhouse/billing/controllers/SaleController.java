package org.coderhouse.billing.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.dtos.SaleRequestDTO;
import org.coderhouse.billing.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @GetMapping("/sales")
    public List<SaleReceiptDTO> getSalesReceiptDTO(){
        List<SaleReceiptDTO> saleReceiptsDTO = saleService.mapSalesReceiptsDTO();
        if (!saleReceiptsDTO.isEmpty()){

            return saleReceiptsDTO;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found sales receipts");
    }

    //create a postMapping method for createsale
    @Operation(
            summary = "Create a new sale ",
            description = "Create a new sale based on the provided SaleRequestDTO",
            operationId = "createSale",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sale created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SaleReceiptDTO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }

    )
    @PostMapping("/sales")
    public ResponseEntity<Object> createSale(@RequestBody SaleRequestDTO saleRequestDTO){
        try {
            //validate fields of saleRequestDTO
            saleService.ValidateSaleRequestDTO(saleRequestDTO);

            //create sale, saleReceipt and saleReceiptDTO
            SaleReceiptDTO saleReceiptDTO = saleService.createSale(saleRequestDTO);

            //return receipt
            return ResponseEntity.ok(saleReceiptDTO);

        } catch (ResponseStatusException ex) {

            // Catches the exception if the salerequestDTO is a Bad Request and returns a 400 response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        }
    }

    @Operation(
            summary = "Deactivate a sale by ID",
            description = "Deactivates the sale identified by the provided ID",
            operationId = "deactivateSale",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sale deactivated successfully"),
                    @ApiResponse(responseCode = "404", description = "Sale not found"),
                    @ApiResponse(responseCode = "404", description = "Sale is not active")
            }
    )
    @PutMapping("/{saleId}/sales")
    public ResponseEntity<Object> deactivateSale(@PathVariable Integer saleId){

        try {

            // call to method to deactivate sale
            saleService.deactivateSale(saleId);

            return ResponseEntity.ok().body("Sale with ID " + saleId + " deactivated successfully");

        } catch (ResponseStatusException ex) {

            // Catches the exception if the sale is not found and returns a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }

    }

}


