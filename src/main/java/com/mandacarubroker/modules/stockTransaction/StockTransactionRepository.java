package com.mandacarubroker.modules.stockTransaction;

import com.mandacarubroker.modules.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface do Repositório de Transações para a entidade "StockTransaction".
 *
 * @author Equipe de desenvolvimento mandacaru
 * @since 1.0.0
 */

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, String> {
    Optional<StockTransaction> findByStockIdOrUserId(String stockId, String userId);

    void deleteByStock(Stock stock);

    void deleteByUserId(String userId);
}