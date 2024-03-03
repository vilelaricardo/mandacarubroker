package com.mandacarubroker.repository;

import com.mandacarubroker.domain.realestatesfunds.RealEstatesFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Real Estates Funds repository.
 **/
@Repository
public interface RealEstatesFundsRepository extends JpaRepository<RealEstatesFunds, String> {
  public RealEstatesFunds getBySymbol(String symbol);
}
