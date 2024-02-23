package org.coderhouse.billing.services;

import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.models.PriceHistory;
import org.coderhouse.billing.models.Product;
import org.coderhouse.billing.repositories.PriceHistoryRepository;
import org.coderhouse.billing.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ExternalWebService externalWebService;

    @Autowired
    PriceHistoryRepository priceHistoryRepository;

    public void saveProduct(Product product){

        productRepository.save(product);

    }

    public Product getProductById(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getIsActive()) {
                return product; // Return the product if it is active

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID: " + productId + " not found");
            }
        }
       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID: " + productId + " not found");
    }

    public List<ProductDTO> getProductDTOList(){

        return productRepository.findAll()
                .stream()
                .filter(Product::getIsActive) // Filter only active products
                .map(product -> new ProductDTO(product))
                .collect(Collectors.toList());

    }

    public boolean isProductExistById(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        return optionalProduct.isPresent();
    }

    public ResponseEntity<Object> stockAvailable(Integer productId, Integer productQuantity){

        //obtain the product
        Product product = getProductById(productId);

        //verify the product isActive
        if (product.getIsActive() && product.getStock() < productQuantity ) {

            return ResponseEntity.ok().build();
        }


        return ResponseEntity.status(HttpStatus.CONFLICT).body("Product not found or Insufficient stock for product with id: " + product.getId());

    }

    //method to update stock
    public ResponseEntity<Object> updateStock(Integer productId, Integer productQuantity) {
        //Check if there is enough stock available
        ResponseEntity<Object> stockAvailabilityResponse = stockAvailable(productId, productQuantity);

        //Check if there was an error while checking stock availability.
        if (stockAvailabilityResponse.getStatusCode() != HttpStatus.OK) {
            // If there is an error, return the same response to report the issue.
            return stockAvailabilityResponse;
        }

        //obtain product
        Product product = getProductById(productId);

        // Subtract the stock quantity
        Integer newStockQuantity = product.getStock() - productQuantity;
        product.setStock(newStockQuantity);

        //Save the updated product to the database
        productRepository.save(product);

        return ResponseEntity.accepted().body("Successful updated stock");

    }

    public Product updateProductPrice(Integer productId, Long newPrice){
        Product product = getProductById(productId);
        if (product != null) {
            List<PriceHistory> priceHistory = product.getPriceHistory();
            PriceHistory newPriceHistory = new PriceHistory(product.getPrice(),externalWebService.getCurrentDate(),product);
            product.addPriceHistory(newPriceHistory); //save de last/actual price of product
            //set new price
            product.setPrice(newPrice); // Actualiza el precio actual
            productRepository.save(product); // Guarda el producto actualizado
            priceHistoryRepository.save(newPriceHistory);
        }
        return product;
    }
}
