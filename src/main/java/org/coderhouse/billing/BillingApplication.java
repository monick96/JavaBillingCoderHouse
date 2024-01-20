package org.coderhouse.billing;

import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository){
        return (args -> {
            //create example client
            //Client client1 = new Client("Maria", "Rojas", "41.255.320",25);

            //save client
           // clientRepository.save(client1);
        });
    }

}
