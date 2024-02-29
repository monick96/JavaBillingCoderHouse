package org.coderhouse.billing.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.services.ClientService;
import org.coderhouse.billing.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private SaleService saleService;

    //obtain all clientsDTO
    @Operation(
            summary = "obtain all clientsDTO",
            description = "Returns a list of all available clients in DTO format.",
            operationId = "getClients",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ClientDTO.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/clients")
    public List<ClientDTO> getClients(){

        //obtain all clients

        return clientService.getClientsDTOList();

    }

    @Operation(summary = "Register a new client",
            description = "Registers a new client with the provided details.",
            operationId = "registerClient",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client registered successfully"),
                    @ApiResponse(responseCode = "409", description = "Conflict: Bad request"),
                    @ApiResponse(responseCode = "409", description = "Conflict: Missing field"),
                    @ApiResponse(responseCode = "409", description = "Conflict: Client already exists")
            }
    )
    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(
            @Parameter(description = "Name of the client", example = "John") @RequestParam String name,
            @Parameter(description = "Last name of the client", example = "Doe") @RequestParam String lastName,
            @Parameter(description = "DNI of the client", example = "12345678") @RequestParam String dni,
            @Parameter(description = "Birthdate of the client", example = "1990-01-01")@RequestParam LocalDate birthdate){
        try{
            Client client = clientService.registerNewClient(name, lastName, dni, birthdate);

            ClientDTO clientDTO = clientService.getClientDTO(client);

            return ResponseEntity.ok(clientDTO);
        }catch (ResponseStatusException ex) {

            // Catches the exception if the client not found and returns a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }

    }

    @PutMapping("/clients")
    @Operation(summary = "Deactivate a client",
            description = "Deactivates a client and all associated active sales.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client and associated sales deactivated successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            })
    public ResponseEntity<Object>deactivateClient(
            @Parameter(description = "ID of the client to deactivate", required = true)
            @RequestParam Integer clientID) {

        try {
            //validate client is saved in DB
            Client client = clientService.getClientById(clientID);

            //deactivate sales actives from this client
            saleService.deactivateActiveSalesForClient(client);

            //deactivate client
            clientService.deactivateClient(client);

            return ResponseEntity.status(HttpStatus.OK).body("Client and Sales deactivated successfully");

        } catch (ResponseStatusException ex) {

            // Catches the exception if the client not found and returns a 404 response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }
    }

}
