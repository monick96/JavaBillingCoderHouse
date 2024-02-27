package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product,Integer> {
    Product getProductByCode(String code);
}
