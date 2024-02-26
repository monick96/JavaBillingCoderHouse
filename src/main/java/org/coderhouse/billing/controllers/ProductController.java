package org.coderhouse.billing.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.models.Product;
import org.coderhouse.billing.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping("/{productId}/products")
    public ResponseEntity<Object> updateProductPrice(@PathVariable Integer productId, @RequestParam Long newPrice) {
        Product updatedProduct = productService.updateProductPrice(productId, newPrice);
        if (updatedProduct != null) {

            return ResponseEntity.ok(updatedProduct);

        } else {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("product not found");
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Object>createProduct(@RequestBody ProductDTO productDTO){

        try {
            productService.createProduct(productDTO);

            return ResponseEntity.ok().body("Product created successfully");

        }catch (ResponseStatusException ex) {

            // Catches the exception if the sale is not found and returns a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }
    }
}
