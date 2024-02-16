package org.coderhouse.billing.services;
import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.dtos.SaleProductDTO;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.models.Sale;
import org.coderhouse.billing.models.SaleProduct;
import org.coderhouse.billing.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleService {
    @Autowired
    SaleRepository saleRepository;


    public void saveSale(Sale sale){

        saleRepository.save(sale);

    }

    //obtain all sales

    public List<SaleReceiptDTO> getSalesReceiptsDTO(){

        return saleRepository.findAll()
                .stream()
                .map(sale -> {
                    int totalProductQuantity = calculateTotalProductQuantity(sale); // Calculate the total number of products sold
                    SaleReceiptDTO saleReceiptDTO = new SaleReceiptDTO(sale);
                    saleReceiptDTO.setTotalProductQuantity(totalProductQuantity); // Assigns the total quantity of products sold to the SaleReceiptDTO

                    return saleReceiptDTO;
                })
                .collect(Collectors.toList());
    }

    //sale receipt by id
    public SaleReceiptDTO generateSaleReceipt(Integer saleId){

        Optional<Sale> optionalSale = saleRepository.findById(saleId);

        //verify the sale is saved in DB;
        if (optionalSale.isPresent()){

            Sale sale = optionalSale.get();

            return new SaleReceiptDTO(sale);

        }else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found.");

        }


    }

    public int calculateTotalProductQuantity(Sale sale) {
        return sale.getSaleProducts()
                .stream()
                .mapToInt(SaleProduct::getQuantity)
                .sum();
    }




}
