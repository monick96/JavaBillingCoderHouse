package org.coderhouse.billing.services;

import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.models.Product;
import org.coderhouse.billing.models.Sale;
import org.coderhouse.billing.models.SaleProduct;
import org.coderhouse.billing.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ExternalWebService externalWebService;

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
                .map(ProductDTO::new)
                .collect(Collectors.toList());

    }

    public boolean isProductExistById(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        return optionalProduct.isPresent();
    }

    public void stockAvailable(Integer productId, Integer productQuantity) {

        // Obtain the product and verify its active status
        Product product = getProductById(productId);

        // Verify stock availability
        if (product.getStock() < productQuantity) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock for product with ID: " + productId);
        }
    }

    //method to update stock
    public void updateStock(Integer productId, Integer productQuantity) {
        try {
            // Check if there is enough stock available
            stockAvailable(productId,productQuantity);

            // Obtain product
            Product product = getProductById(productId);

            // Subtract the stock quantity
            Integer newStockQuantity = product.getStock() - productQuantity;
            product.setStock(newStockQuantity);

            //Save the updated product to the database
            productRepository.save(product);

        } catch (ResponseStatusException ex) {
            // Log the exception or perform any necessary actions
            ex.printStackTrace();
            throw ex; // Re-throw the exception to propagate it
        }

    }

    public Product updateProductPrice(Integer productId, Long newPrice){
        Product product = getProductById(productId);
        if (product != null && !Objects.equals(product.getPrice(), newPrice)) {
            //set new price
            product.setPrice(newPrice); // update current price
            productRepository.save(product); // save update product price
        }
        return product;
    }

    //method calculate total product quantity
    public int calculateTotalProductQuantity(Sale sale) {
        return sale.getSaleProducts()
                .stream()
                .mapToInt(SaleProduct::getQuantity)
                .sum();
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        //Verify that the tDTO object is not null
        if (productDTO == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProductDTO is null");

        }

        Product product = productRepository.getProductByCode(productDTO.getCode());

        if (product != null) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product wit code: " + productDTO.getCode() + " already exist");
        }


        Product newProduct = new Product(productDTO.getName(), productDTO.getStock(), productDTO.getCode(),
                productDTO.getDescription(), productDTO.getPrice());

        newProduct.setIsActive(true);

        saveProduct(newProduct);
        return productDTO;
    }

}
