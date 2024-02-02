package org.coderhouse.billing.services;
import org.coderhouse.billing.dtos.ProductDTO;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.models.Sale;
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
                .map(sale -> new SaleReceiptDTO(sale))
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



    //function using map and stream()
//    public List<SaleDTO> getSalesDTOsList(){
//        //obtain all sales order by date
//        List<Sale> sortedSales = saleRepository.findAllByOrderByDateAsc();
//
//        //create a SaleDTO list using map and stream()
//        List<SaleDTO> salesDTO = sortedSales.stream() //sequence of elements on which high-level operations can be performed
//                .map(sale -> new SaleDTO(sale))// Convert each Sale to SaleDTO using its constructor
//                .collect(Collectors.toList()); // Collect the SaleDTOs in a list
//
//        return salesDTO;
//
//    }
    //function using loop
//    public List<SaleDTO> getSalesDTOsList(){
//        //obtain all sales
//        List<Sale> sales = saleRepository.findAll();
//
//        //order by date
//        sales.sort((sale1, sale2) -> sale1.getDate().compareTo(sale2.getDate()));
//
//        //create a new list sale dto type
//        List<SaleDTO> sortedSalesDTO = new ArrayList<>();
//
//        //for each sale en sortedSales create a new object type SaleDTO
//        //and save de new objet type SaleDTO to the list salesDTO
//        for (Sale sale: sales){
//
//            SaleDTO saleDTO = new SaleDTO(sale);
//
//            sortedSalesDTO.add(saleDTO);
//        }
//
//        return sortedSalesDTO;
//
//    }
}
