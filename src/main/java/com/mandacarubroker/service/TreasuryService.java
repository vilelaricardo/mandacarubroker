package com.mandacarubroker.service;

import com.mandacarubroker.domain.treasury.RegisterDataTransferObject;
import com.mandacarubroker.domain.treasury.Treasury;
import com.mandacarubroker.repository.TreasuryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The treasury service method.
 **/
@Service
public class TreasuryService {

  private final TreasuryRepository repository;

  public TreasuryService(
      TreasuryRepository treasuryRepository
  ) {
    this.repository = treasuryRepository;
  }

  public List<Treasury> getAll() {
    return repository.findAll();
  }

  public Optional<Treasury> get(String id) {
    return repository.findById(id);
  }

  public Treasury create(RegisterDataTransferObject data) {
    Treasury newRegister = new Treasury(data);

    return repository.save(newRegister);
  }

  public Optional<Treasury> update(String id, RegisterDataTransferObject data) {
    return repository.findById(id)
        .map(item -> {
          item.setName(data.name());
          item.setType(data.type());
          item.setAmount(data.amount());
          item.setPrice(data.price());
          item.setInterest_rate(data.interest_rate());
          item.setMaturity_date(data.maturity_date());

          return repository.save(item);
        });
  }

  public void delete(String id) {
    repository.deleteById(id);
  }
}
