package com.mandacarubroker.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface do Repositório de Ações para a entidade "Stock".
 *
 * @author Equipe de desenvolvimento mandacaru
 * @since 1.0.0
 */

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}