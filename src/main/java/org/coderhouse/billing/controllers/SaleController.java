package org.coderhouse.billing.controllers;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SaleController {
    @Autowired
    SaleService saleService;

    //obtain all salesReceiptDTO
    @GetMapping("/salesReceipts")
    public List<SaleReceiptDTO> getSalesReceiptDTO(){

       return saleService.getSalesReceiptsDTO();

    }

}
