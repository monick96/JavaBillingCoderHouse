package org.coderhouse.billing.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    //obtain all ProductsDTO
    @Operation(
            summary = "obtain all ProductDTO",
            description = "Returns a list of all available products in DTO format.",
            operationId = "getProducts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ProductDTO.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/products")
    //ResponseEntity allows you to control and customize the HTTP response
    public List<ProductDTO> getProductList(){

        List<ProductDTO> productsDTOs = productService.getProductDTOList();

        return productsDTOs;

    }
}
