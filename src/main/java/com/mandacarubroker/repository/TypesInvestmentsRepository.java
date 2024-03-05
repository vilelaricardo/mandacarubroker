package com.mandacarubroker.repository;

import com.mandacarubroker.domain.typesinvestiments.TypesInvestments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Types investments Repository interface.
 **/
@Repository
public interface TypesInvestmentsRepository extends JpaRepository<TypesInvestments, String> {}
