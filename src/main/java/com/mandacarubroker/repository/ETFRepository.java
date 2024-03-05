package com.mandacarubroker.repository;

import com.mandacarubroker.domain.etf.ETF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The ETF repository.
 **/
@Repository
public interface ETFRepository extends JpaRepository<ETF, String> {
  public ETF getBySymbol(String symbol);
}
