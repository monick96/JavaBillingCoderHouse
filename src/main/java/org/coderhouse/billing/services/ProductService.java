package org.coderhouse.billing.services;

import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.models.Product;
import org.coderhouse.billing.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void saveProduct(Product product){

        productRepository.save(product);

    }

    public List<ProductDTO> getProductDTOList(){
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDTO(product))
                .collect(Collectors.toList());
    }
}
