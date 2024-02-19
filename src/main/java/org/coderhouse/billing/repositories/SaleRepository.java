package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.Client;
import org.coderhouse.billing.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface SaleRepository extends JpaRepository<Sale,Integer> {

    List <Sale> findByClientAndIsActiveTrue(Client client);

    List<Sale> findByIsActiveTrue();


}
