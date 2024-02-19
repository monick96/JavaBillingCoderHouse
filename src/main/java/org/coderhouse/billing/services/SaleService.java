package org.coderhouse.billing.services;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.models.Sale;
import org.coderhouse.billing.models.SaleProduct;
import org.coderhouse.billing.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    //verify client has sale by client an is active true
    public List<Sale> getActiveSales(Client client) {
        // Verificar si el cliente tiene ventas activas
        return saleRepository.findByClientAndIsActiveTrue(client);
    }


    //obtain all sales

    public List<SaleReceiptDTO> getSalesReceiptsDTO(){
        List<Sale> activeSales = saleRepository.findByIsActiveTrue();

        if (!activeSales.isEmpty()) {
            return activeSales .stream()
                    .map(sale -> {
                        int totalProductQuantity = calculateTotalProductQuantity(sale); // Calculate the total number of products sold
                        SaleReceiptDTO saleReceiptDTO = new SaleReceiptDTO(sale);
                        saleReceiptDTO.setTotalProductQuantity(totalProductQuantity); // Assigns the total quantity of products sold to the SaleReceiptDTO

                        return saleReceiptDTO;
                    })
                    .collect(Collectors.toList());

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No active sales found.");
        }

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
