package org.coderhouse.billing.controllers;

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

    @GetMapping("/products")
    //ResponseEntity allows you to control and customize the HTTP response
    public List<ProductDTO> getProductList(){

        List<ProductDTO> productsDTOs = productService.getProductDTOList();

        return productsDTOs;

    }
}
