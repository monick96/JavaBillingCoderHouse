package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.SaleReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleReceiptRepository extends JpaRepository<SaleReceipt,Integer> {


}
