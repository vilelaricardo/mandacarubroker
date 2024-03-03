package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.repository.StockRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
* The stock service method.
**/
@Service
public class StockService {

  private final StockRepository stockRepository;

  public StockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public List<Stock> getAllStocks() {
    return stockRepository.findAll();
  }

  public Optional<Stock> getStockById(String id) {
    return stockRepository.findById(id);
  }

  /**
  * The method create a stock.
  *
  *  @param data The stock data
  *
  **/
  public Stock createStock(RequestStockDataTransferObject data) {
    Stock newStock = new Stock(data);
    validateRequestStockDataTransferObject(data);
    return stockRepository.save(newStock);
  }

  /**
  * The method update the stock.
  *
  * @param id the stock id
  * @param updatedStock the stock data modified
  * @return The stock repository
  **/
  public Optional<Stock> updateStock(String id, Stock updatedStock) {
    return stockRepository.findById(id)
            .map(stock -> {
              stock.setSymbol(updatedStock.getSymbol());
              stock.setCompanyName(updatedStock.getCompanyName());
              stock.setPrice(updatedStock.getPrice());
              stock.setAmount(updatedStock.getAmount());

              return stockRepository.save(stock);
            });
  }

  public void deleteStock(String id) {
    stockRepository.deleteById(id);
  }

  /**
  * The method validates the data request stock.
  *
  * @param data the data stock
  *
  **/
  public static void validateRequestStockDataTransferObject(RequestStockDataTransferObject data) {
    Validator validator;
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
    Set<ConstraintViolation<RequestStockDataTransferObject>> violations = validator.validate(data);

    if (!violations.isEmpty()) {
      StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

      for (ConstraintViolation<RequestStockDataTransferObject> violation : violations) {
        errorMessage.append(
            String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage())
        );
      }

      errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

      throw new ConstraintViolationException(errorMessage.toString(), violations);
    }
  }
}
