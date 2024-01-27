package org.coderhouse.billing.services;

import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service //To be able to autowire ClientService in ClientController,
        // it is very important that you have this bean @Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository; //allows access to methods and functionality
                                                // provided by ClientRepository within ClientService

    public void saveClient(Client client){

        clientRepository.save(client);

    }

    //method to get all clients
    public List<ClientDTO> getClientsDTOList() {

        //VI get the list of clients and map
        // it to data objects that only bring the data I need
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());

    }

    //method to calculate age
    public Integer calculateAge(Client client) {

        //calculate the difference between the current date
        // and the client's date of birth with Period.between()

        LocalDate currentDate = LocalDate.now();
        return Period.between(client.getBirthdate(),currentDate).getYears();

    }

}
