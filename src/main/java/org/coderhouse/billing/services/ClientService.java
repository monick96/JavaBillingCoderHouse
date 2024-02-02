package org.coderhouse.billing.services;

import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
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

        //I get the list of clients and map
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
        int age = 0;

        //validate client before calculate age
        if (isClientValid(client)){

            LocalDate currentDate = LocalDate.now();

            age =  Period.between(client.getBirthdate(),currentDate).getYears();

        }

        return age;

    }

    public boolean isClientValid(Client client){

       Optional<Client> checkClient = clientRepository.findById(client.getId());

        //verify the client is saved in DB;
        if (checkClient.isPresent()){

            return true;

        }else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");

        }

    }

}
