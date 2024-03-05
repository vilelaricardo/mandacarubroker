package com.mandacarubroker.repository;

import com.mandacarubroker.domain.treasury.Treasury;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The treasury repository.
 **/
@Repository
public interface TreasuryRepository extends JpaRepository<Treasury, String> {
  public Treasury getByName(String name);
}
