package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import com.mandacarubroker.dto.RequestStockDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * Sistema de serviços para as ações.
 */
@Service
public class StockService {
  private final StockRepository stockRepository;

  public StockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  /**
   * Recupera todas as ações.
   *
   * @return lista de todas as ações
   */
  public List<Stock> getAllStocks() {
    return stockRepository.findAll();
  }

  /**
   * Recupera uma ação pelo identificador ID.
   *
   * @param id ID da ação
   * @return ação encontrada, ou Optional.empty() se não encontrada
   */
  public Optional<Stock> getStockById(String id) {
    return stockRepository.findById(id);
  }

  /**
   * Cria uma nova ação.
   *
   * @param data DTO contendo os dados da nova ação
   * @return ação criada
   * @throws ConstraintViolationException se a validação dos dados falhar
   */
  public Stock createStock(RequestStockDTO data) {
    Stock newStock = new Stock(data);
    validateRequestStockDTO(data);
    return stockRepository.save(newStock);
  }

  /**
   * Atualiza uma ação.
   *
   * @param id identificador da ação
   * @param updatedStock dados atualizados da ação
   * @return ação atualizada, ou Optional.empty() se não encontrada
   */
  public Optional<Stock> updateStock(String id, Stock updatedStock) {
    return stockRepository.findById(id)
            .map(stock -> {
              stock.setSymbol(updatedStock.getSymbol());
              stock.setCompanyName(updatedStock.getCompanyName());
              stock.setPrice(updatedStock.getPrice()); // Substitui o preço

              return stockRepository.save(stock);
            });
  }

  /**
   * Deleta uma ação.
   *
   * @param id identificador da ação
   */
  public void deleteStock(String id) {
    stockRepository.deleteById(id);
  }

  /**
   * Valida os dados de um DTO de requisição de ação.
   *
   * @param data DTO contendo os dados da ação
   * @throws ConstraintViolationException se a validação dos dados falhar
   */
  public static void validateRequestStockDTO(RequestStockDTO data) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

    if (!violations.isEmpty()) {
      StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

      for (ConstraintViolation<RequestStockDTO> violation : violations) {
        errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
      }

      errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

      throw new ConstraintViolationException(errorMessage.toString(), violations);
    }
  }

  /**
   * Valida e cria uma nova ação.
   *
   * @param data DTO contendo os dados da nova ação
   * @throws ConstraintViolationException se a validação dos dados falhar
   */
  public void validateAndCreateStock(RequestStockDTO data) {
    validateRequestStockDTO(data);

    Stock newStock = new Stock(data);
    stockRepository.save(newStock);
  }
}