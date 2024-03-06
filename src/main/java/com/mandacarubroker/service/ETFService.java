package com.mandacarubroker.service;

import com.mandacarubroker.domain.etf.ETF;
import com.mandacarubroker.domain.etf.RegisterDataTransferObject;
import com.mandacarubroker.repository.ETFRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The ETF Service.
 **/
@Service
public class ETFService {
  private final ETFRepository repository;

  public ETFService(
      ETFRepository etfRepository
  ) {
    this.repository = etfRepository;
  }

  public List<ETF> getAll() {
    return repository.findAll();
  }

  public Optional<ETF> get(String id) {
    return repository.findById(id);
  }

  public ETF create(RegisterDataTransferObject data) {
    ETF newRegister = new ETF(data);

    return repository.save(newRegister);
  }

  public Optional<ETF> update(String id, RegisterDataTransferObject data) {
    return repository.findById(id)
        .map(item -> {
          item.setName(data.name());
          item.setSymbol(data.symbol());
          item.setCompanyName(data.companyName());
          item.setAmount(data.amount());
          item.setPrice(data.price());

          return repository.save(item);
        });
  }

  public void delete(String id) {
    repository.deleteById(id);
  }
}
