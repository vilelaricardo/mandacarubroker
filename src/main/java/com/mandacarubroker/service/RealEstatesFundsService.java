package com.mandacarubroker.service;

import com.mandacarubroker.domain.realestatesfunds.RealEstatesFunds;
import com.mandacarubroker.domain.realestatesfunds.RegisterDataTransferObject;
import com.mandacarubroker.repository.RealEstatesFundsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RealEstatesFundsService {
  private final RealEstatesFundsRepository repository;

  public RealEstatesFundsService(
      RealEstatesFundsRepository realEstatesFundsRepository
  ) {
    this.repository = realEstatesFundsRepository;
  }

  public List<RealEstatesFunds> getAll() {
    return repository.findAll();
  }

  public Optional<RealEstatesFunds> get(String id) {
    return repository.findById(id);
  }

  public RealEstatesFunds create(RegisterDataTransferObject data) {
    RealEstatesFunds newRegister = new RealEstatesFunds(data);

    return repository.save(newRegister);
  }

  public Optional<RealEstatesFunds> update(String id, RegisterDataTransferObject data) {
    return repository.findById(id)
        .map(item -> {
          item.setName(data.name());
          item.setSymbol(data.symbol());
          item.setCompanyName(data.companyName());
          item.setType(data.type());
          item.setAmount(data.amount());
          item.setPrice(data.price());

          return repository.save(item);
        });
  }

  public void delete(String id) {
    repository.deleteById(id);
  }
}
