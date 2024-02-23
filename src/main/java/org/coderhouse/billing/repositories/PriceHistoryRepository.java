package org.coderhouse.billing.repositories;

import org.coderhouse.billing.models.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
}
