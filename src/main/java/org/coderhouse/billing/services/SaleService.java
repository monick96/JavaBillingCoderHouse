package org.coderhouse.billing.services;
import org.apache.commons.lang3.math.NumberUtils;
import org.coderhouse.billing.dtos.SaleItemDTO;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.dtos.SaleRequestDTO;
import org.coderhouse.billing.models.*;
import org.coderhouse.billing.repositories.SaleProductRepository;
import org.coderhouse.billing.repositories.SaleReceiptRepository;
import org.coderhouse.billing.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleService {
    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    ProductService productService;

    @Autowired
    ExternalWebService externalWebService;

    @Autowired
    SaleProductRepository saleProductRepository;

    @Autowired
    SaleReceiptRepository saleReceiptRepository;


    public void saveSale(Sale sale){

        saleRepository.save(sale);

    }

    //verify client has sale by client an is active true
    public List<Sale> getActiveSales(Client client) {
        // Verificar si el cliente tiene ventas activas
        return saleRepository.findByClientAndIsActiveTrue(client);
    }


    //obtain all sales
    public List<SaleReceiptDTO> getSalesReceiptsDTO(){
        List<SaleReceipt> saleReceipts = saleReceiptRepository.findAll();

        if (!saleReceipts.isEmpty()) {
            return saleReceipts .stream()
                    .map(SaleReceiptDTO::new)
                    .collect(Collectors.toList());

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No found receipts.");
        }

    }

    // receive DTO from sales to generate receipt
    public ResponseEntity<Object> createSale(SaleRequestDTO saleRequestDTO){

        ValidSaleRequestDTO(saleRequestDTO);

        //create a new sale object
        Sale newSale = new Sale();

        // Assign the customer to the Sale object
        Integer clientId = saleRequestDTO.getClientId();

        Client client = clientService.getClientById(clientId);

        newSale.setClient(client);

        saveSale(newSale);

        SaleReceipt saleReceipt = new SaleReceipt(newSale);
        saleReceiptRepository.save(saleReceipt);

        // Iterate over each SaleItemDTO and add it to the sale
        for(SaleItemDTO saleItemDTO : saleRequestDTO.getSales()){
            //Assign the product to saleproduct object
            Integer productId = saleItemDTO.getProductId();
            Product product = productService.getProductById(productId);
            //create saleProduct from saleitemDTO
            SaleProduct newSaleProduct = new SaleProduct(product,newSale,saleItemDTO.getQuantity());

            newSaleProduct.setPrice(product.getPrice());

            //assign saleitem to sale
            newSale.addSaleProduct(newSaleProduct);

            //save item to product
            product.addSaleProduct(newSaleProduct);

            //save item to salereceipt
            saleReceipt.addSaleProduct(newSaleProduct);

            //save saleproduct in db
            saleProductRepository.save(newSaleProduct);

            productService.saveProduct(product);



        }



        //generate date for the buy
        LocalDateTime datePurchase = externalWebService.getCurrentDate();
        //assign date to sale
        newSale.setDate(datePurchase);

        //calculate total
        newSale.calculateTotal();

        newSale.setAmount(productService.calculateTotalProductQuantity(newSale));

        newSale.setIsActive(true);

        saveSale(newSale);

        saleReceipt.setTotalProductQuantity(productService.calculateTotalProductQuantity(newSale));
        saleReceipt.setDate(datePurchase);
        saleReceipt.setTotalPurchase(newSale.getTotal());

        saleReceiptRepository.save(saleReceipt);

        SaleReceiptDTO saleReceiptDTO = new SaleReceiptDTO(saleReceipt);

        return ResponseEntity.ok(saleReceiptDTO);

    }

    //validate SaleRequestDTO
    public ResponseEntity<Object> ValidSaleRequestDTO(SaleRequestDTO saleRequestDTO) {
        //Verify that the SaleRequestDTO object is not null
        if (saleRequestDTO == null) {

            return ResponseEntity.badRequest().body("SaleRequestDTO is null");

        }

        //Verify that the ClientId in SaleRequestDTO is not null and has a valid ID
        if (saleRequestDTO.getClientId() == null || saleRequestDTO.getClientId() <= 0) {

            return ResponseEntity.badRequest().body("Invalid Client ID in SaleRequestDTO");


        }

        //validate if client exist in DB
        clientService.isClientValidById(saleRequestDTO.getClientId());

        // Verify that the list of sales items is not null or empty
        if (saleRequestDTO.getSales() == null || saleRequestDTO.getSales().isEmpty()) {

            return ResponseEntity.badRequest().body("Sales list is null or empty");


        }

        // Check each sales item on the list
        for (SaleItemDTO saleItemDTO : saleRequestDTO.getSales()) {
            // Verify that the quantity is a positive and integer number
            if (!NumberUtils.isCreatable(String.valueOf(saleItemDTO.getQuantity()))) {

                return ResponseEntity.badRequest().body("Quantity must be a number");


            }

            int quantity = Integer.parseInt(String.valueOf(saleItemDTO.getQuantity()));

            if (quantity <= 0) {

                return ResponseEntity.badRequest().body("Quantity must be a positive integer");

            }
        }


        // Verify that the productId in SaleItemDTO is not null and has a valid ID
        for (SaleItemDTO saleItemDTO: saleRequestDTO.getSales()){

            if(saleItemDTO.getProductId()==null){

                return ResponseEntity.badRequest().body("Product ID in SaleItemDTO must not be null");


            }

            // Verify that productId is a positive integer
            if (!NumberUtils.isDigits(String.valueOf(saleItemDTO.getProductId())) || Integer.parseInt(String.valueOf(saleItemDTO.getProductId())) <= 0) {

                return ResponseEntity.badRequest().body("Invalid Product ID in SaleItemDTO");


            }
            //verify that the product exist in db
            if (!productService.isProductExistById(saleItemDTO.getProductId())){

                return ResponseEntity.badRequest().body("Product with ID " + saleItemDTO.getProductId() + " not found");


            }

            //verify stock is available
            productService.stockAvailable(saleItemDTO.getProductId(), saleItemDTO.getQuantity());

            //update stock in each item
            productService.updateStock(saleItemDTO.getProductId(), saleItemDTO.getQuantity());

        }

        // If it passes all the checks, consider it valid
        return ResponseEntity.ok().build();
    }



    //sale receipt by id
    public SaleReceiptDTO generateSaleReceipt(Integer saleId){

        Optional<Sale> optionalSale = saleRepository.findById(saleId);

        //verify the sale is saved in DB;
        if (optionalSale.isPresent()){

            Sale sale = optionalSale.get();

            SaleReceipt saleReceipt = new SaleReceipt(sale);

            saleReceiptRepository.save(saleReceipt);

            return new SaleReceiptDTO(saleReceipt);

        }else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found.");

        }


    }




}
