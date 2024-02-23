package org.coderhouse.billing.services;

import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    //@Autowired
   // private SaleService saleService;

    public void saveClient(Client client){

        clientRepository.save(client);

    }

    //get client by id

    public Client getClientById(Integer clientId){
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            return optionalClient.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
    }

    //method to get all clients
    public List<ClientDTO> getClientsDTOList() {

        //I get the list of clients and map
        // it to data objects that only bring the data I need
        List<Client> activeClients = clientRepository.findByIsActiveTrue();

        if (!activeClients.isEmpty()) {
            return activeClients
                    .stream()
                    .map(client -> new ClientDTO(client))
                    .collect(Collectors.toList());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No active clients found.");
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

    ///validate by id
    public boolean isClientValidById(Integer clientId){

        Optional<Client> checkClient = clientRepository.findById(clientId);

        //verify the client is saved in DB;
        if (checkClient.isPresent()){

            return true;

        }else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");

        }

    }
    
    public ResponseEntity<Object> registerNewClient(String name,  String lastName,String dni, LocalDate birthdate){

        //validate params is not null
        if (name.isBlank() || lastName.isBlank() || dni.isBlank() || birthdate == null) {

            String missingField = "";

            if (name.isBlank()) {

                missingField = "Name";

            } else if (lastName.isBlank()) {

                missingField = "Last Name";

            } else if (dni.isBlank()) {

                missingField = "DNI";

            } else if (birthdate == null) {

                missingField = "Birthdate";

            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body(missingField + " is missing");

        }

        //validate client exist by DNI
        if (clientRepository.getClientByDni(dni) !=  null) {

            //return new ResponseEntity<>("mail already in use", HttpStatus.FORBIDDEN);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Client already exist");

        }

        //create client with params
        Client newClient = new Client(name, lastName, dni, birthdate);

        //save client
        saveClient(newClient);

        //calculate age
        newClient.setAge(calculateAge(newClient));

        //activate client
        newClient.setIsActive(true);

        //save client
        saveClient(newClient);

        //return response successful registration
        return ResponseEntity.status(HttpStatus.CREATED).body("Successful registration");

    }

//    public ResponseEntity<Object> deactivateClient(Integer clientID){
//        //validate client is saved in DB
//        Client client = clientRepository.findById(clientID)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
//
//        if (client.getIsActive()) {
//            //verify client has active sales
//            List <Sale> activeSales = saleService.getActiveSales(client);
//
//            if (!activeSales.isEmpty()) {
//
//                activeSales.forEach(sale -> {
//                    // Deactivate each active sale
//                    sale.setIsActive(false);
//
//                    // save changes in sales
//                    saleService.saveSale(sale);
//                });
//
//            }
//
//            // Desactivar el cliente
//            client.setIsActive(false);
//            saveClient(client);
//
//            return ResponseEntity.status(HttpStatus.OK).body("Client and Sales deactivated successfully");
//
//        }else {
//
//            // The client is not active, so we return a "Bad Request" error
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client is not active and cannot be deactivated");
//
//        }
//
//    }



}
