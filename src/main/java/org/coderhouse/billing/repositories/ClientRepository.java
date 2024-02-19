package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    Client getClientByDni (String dni);
    List<Client> findByIsActiveTrue ();
}
