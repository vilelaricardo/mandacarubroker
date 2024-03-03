package com.mandacarubroker.repository;


import com.mandacarubroker.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
* The Stock Repository interface.
**/
@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
  public Stock getBySymbol(String symbol);
}
