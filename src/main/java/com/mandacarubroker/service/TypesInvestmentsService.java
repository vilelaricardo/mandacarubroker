package com.mandacarubroker.service;

import com.mandacarubroker.domain.typesinvestiments.RegisterTypeInvestment;
import com.mandacarubroker.domain.typesinvestiments.TypesInvestments;
import com.mandacarubroker.repository.TypesInvestmentsRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The Type investments service.
 **/
@Service
public class TypesInvestmentsService {
  private final TypesInvestmentsRepository investmentsRepository;

  public TypesInvestmentsService(
      TypesInvestmentsRepository typesInvestmentsRepository
  ) {
    this.investmentsRepository = typesInvestmentsRepository;
  }

  public List<TypesInvestments> getAllInvestments() {
    return investmentsRepository.findAll();
  }
  
  public TypesInvestments create(RegisterTypeInvestment data) {
    TypesInvestments newRegister = new TypesInvestments(data);
    return investmentsRepository.save(newRegister);
  }

  public void delete(String id) {
    investmentsRepository.deleteById(id);
  }
}
