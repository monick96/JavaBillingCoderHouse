package org.coderhouse.billing.services;

import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.models.Product;
import org.coderhouse.billing.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void saveProduct(Product product){

        productRepository.save(product);

    }

    public Product getProductById(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {

            return optionalProduct.get(); // Return the product if is present

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    public List<ProductDTO> getProductDTOList(){

        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDTO(product))
                .collect(Collectors.toList());

    }

    public boolean isProductExistById(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        return optionalProduct.isPresent();
    }

    public boolean isStockAvailable(Integer productId,Integer productQuantity){

        //obtain the product
        Product product = getProductById(productId);

        //verify the product isActive
        if (!product.getIsActive()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found.");
        }

        // Check stock availability
        if (product.getStock() < productQuantity) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock for product with id: " + product.getId());

        }

        return true;

    }

    //method to update stock
    public void updateStock(Integer productId, Integer productQuantity) {

        if (isStockAvailable(productId,productQuantity)) {
            //obtain product
            Product product = getProductById(productId);

            // Subtract the stock quantity
            Integer newStockQuantity = product.getStock() - productQuantity;
            product.setStock(newStockQuantity);

            //Save the updated product to the database
            productRepository.save(product);

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or insufficient stock");

    }
}
