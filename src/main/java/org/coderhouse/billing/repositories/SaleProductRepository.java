package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct,Integer> {
}
