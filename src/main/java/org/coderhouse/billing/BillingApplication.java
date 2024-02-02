package org.coderhouse.billing;

import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.models.Product;
import org.coderhouse.billing.models.Sale;
import org.coderhouse.billing.models.SaleProduct;
import org.coderhouse.billing.repositories.ClientRepository;
import org.coderhouse.billing.repositories.ProductRepository;
import org.coderhouse.billing.repositories.SaleProductRepository;
import org.coderhouse.billing.repositories.SaleRepository;
import org.coderhouse.billing.services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientService clientService, ClientRepository clientRepository, ProductRepository productRepository, SaleRepository saleRepository, SaleProductRepository saleProductRepository) {
        return (args -> {
            //create example client
//            Client client2 = new Client("Maria", "Rosas", "41.255.320", LocalDate.of(1996, 1, 16));
//            client2.setAge(clientService.calculateAge(client2));
//
//            clientRepository.save(client2);
//
//            //create product
//            Product smartTV22 = new Product("Smart tv 22\"", 800, 9999, "smart tv 22 inches, no delivery", 150000L);
//            //save product
//            productRepository.save(smartTV22);
//
//            //create sale
//            Sale sale1 = new Sale(500, LocalDateTime.now());
//            saleRepository.save(sale1);
//
//            //add sale to client
//            client2.addSale(sale1);
//
//            //save client
//            clientRepository.save(client2);
//
//            //set saleproduct
//            SaleProduct saleProduct1 = new SaleProduct(smartTV22, sale1, sale1.getAmount());
//            //addsaleproduct to sale
//            sale1.addSaleProduct(saleProduct1);
//            //add saleproduct to product
//            smartTV22.addSaleProduct(saleProduct1);
//            //saveSaleproduct
//            saleProductRepository.save(saleProduct1);
//
//            //saves
//            saleRepository.save(sale1);
//            productRepository.save(smartTV22);
//
//            //set  total sale
//            sale1.calculateTotal();
//
//            //save sale
//            saleRepository.save(sale1);


            //prints
//            List <Client> clientList = new ArrayList<>();
//            clientList = clientRepository.findAll();
//            System.out.println(clientList);
//            System.out.println(clientList.get(0).toString());
//            Client searchClient = clientList.get(0);
//            System.out.println(searchClient.getId());
//
           // System.out.println(client2.getSales()); //Java Use Sale's toString() to display details


        });
    }

}
