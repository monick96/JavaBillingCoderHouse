package org.coderhouse.billing;

import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.models.Product;
import org.coderhouse.billing.models.Sale;
import org.coderhouse.billing.models.SaleProduct;
import org.coderhouse.billing.repositories.ClientRepository;
import org.coderhouse.billing.repositories.ProductRepository;
import org.coderhouse.billing.repositories.SaleProductRepository;
import org.coderhouse.billing.repositories.SaleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, ProductRepository productRepository, SaleRepository saleRepository, SaleProductRepository saleProductRepository){
        return (args -> {
            //create example client
             Client client2 = new Client("Maria", "Rosas", "41.255.320",25);
            clientRepository.save(client2);


//            List <Client> clientList = new ArrayList<>();
//            clientList = clientRepository.findAll();
//            System.out.println(clientList);
//            System.out.println(clientList.get(0).toString());
//            Client searchClient = clientList.get(0);
//            System.out.println(searchClient.getId());

            //create product
            Product tv22= new Product("Smart tv 22 pulgadas",800,9999,"smart tv 22 pulgadas, no delivery", 150000L);
            //save product
            productRepository.save(tv22);
            //create sale
            Sale sale1 = new Sale(500, LocalDateTime.now());
            saleRepository.save(sale1);
            sale1.setTotal(sale1.getAmount()*tv22.getPrice());
            saleRepository.save(sale1);

            //add sale to client
            client2.addSale(sale1);

            saleRepository.save(sale1);

            //save client
            clientRepository.save(client2);

            //System.out.println(client2.getSales());




//            //set saleproduct
            SaleProduct saleProduct1 = new SaleProduct(tv22,sale1);
            //addsaleproduct to sale
            sale1.addSaleProduct(saleProduct1);
            //add saleproduct to product
            tv22.addSaleProduct(saleProduct1);
            //saveSaleproduct
            saleProductRepository.save(saleProduct1);

            //saves
            saleRepository.save(sale1);
            productRepository.save(tv22);


        });
    }

}
