package com.mandacarubroker.modules.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface do Repositório de Ações para a entidade "Stock".
 *
 * @author Equipe de desenvolvimento mandacaru
 * @since 1.0.0
 */

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findBySymbol(String symbol);
}