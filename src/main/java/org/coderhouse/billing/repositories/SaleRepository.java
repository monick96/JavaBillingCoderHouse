package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale,Integer> {
}
