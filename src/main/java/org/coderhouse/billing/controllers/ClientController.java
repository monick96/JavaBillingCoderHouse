package org.coderhouse.billing.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.coderhouse.billing.dtos.ClientDTO;
import org.coderhouse.billing.dtos.SaleReceiptDTO;
import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.repositories.ClientRepository;
import org.coderhouse.billing.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

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
        List<ClientDTO> clientList = clientService.getClientsDTOList();

        return  clientList;

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
            @RequestParam String name, @RequestParam String lastName,
            @RequestParam String dni, @RequestParam LocalDate birthdate){

        return clientService.registerNewClient(name, lastName, dni, birthdate);

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
            @RequestParam Integer clientID){

        return clientService.deactivateClient(clientID);

    }





}
