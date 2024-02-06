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
import org.coderhouse.billing.services.ExternalWebService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public CommandLineRunner initData(ClientService clientService, ClientRepository clientRepository, ProductRepository productRepository, SaleRepository saleRepository, SaleProductRepository saleProductRepository, ExternalWebService externalWebService) {
        return (args -> {
//            //create example client
//            Client client2 = new Client("Maria", "Rosas", "41.255.320", LocalDate.of(1996, 1, 16));
//
//            clientRepository.save(client2);
//
//            client2.setAge(clientService.calculateAge(client2));
//
//            //create product
//            Product smartTV22 = new Product("Smart tv 22\"", 800, 9999, "smart tv 22 inches, no delivery", 150000L);
//            //save product
//            productRepository.save(smartTV22);
//
//            //create sale
//            Sale sale1 = new Sale(500, externalWebService.getCurrentDate());
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
//
            //create example client
//            Client client3 = new Client("Mario", "Rojas", "38.255.320", LocalDate.of(1997, 1, 16));
//            clientRepository.save(client3);
//
//            client3.setAge(clientService.calculateAge(client3));
//
//            //create product
//            Product smartTV40 = new Product("Smart tv 40\"", 800, 9989, "smart tv 40 inches, no delivery", 350000L);
//            //save product
//            productRepository.save(smartTV40);
////
////            //create sale
//            Sale sale2 = new Sale(100, externalWebService.getCurrentDate());
//            saleRepository.save(sale2);
//
//            //add sale to client
//            client3.addSale(sale2);
//
//            //save client
//            clientRepository.save(client3);
//
//
////            //set saleproduct
//            SaleProduct saleProduct2 = new SaleProduct(smartTV40, sale2, sale2.getAmount());
////            //addsaleproduct to sale
//            sale2.addSaleProduct(saleProduct2);
////            //add saleproduct to product
//            smartTV40.addSaleProduct(saleProduct2);
////            //saveSaleproduct
//            saleProductRepository.save(saleProduct2);
////
////            //saves
//            saleRepository.save(sale2);
//            productRepository.save(smartTV40);
////
////            //set  total sale
//            sale2.calculateTotal();
////
////            //save sale
//            saleRepository.save(sale2);


            //prints
//            List <Client> clientList = new ArrayList<>();
//            clientList = clientRepository.findAll();
//            System.out.println(clientList);
//            System.out.println(clientList.get(0).toString());
//            Client searchClient = clientList.get(0);
//            System.out.println(searchClient.getId());
//
           // System.out.println(client3.getSales()); //Java Use Sale's toString() to display details


        });
    }

}
