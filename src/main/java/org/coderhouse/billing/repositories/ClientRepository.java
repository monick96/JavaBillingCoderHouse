package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    Client getClientByDni (String dni);
}
