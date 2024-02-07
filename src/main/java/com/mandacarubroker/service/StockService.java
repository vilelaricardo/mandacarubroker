package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public final class StockService {
    private final StockRepository stockRepository;

    public StockService(final StockRepository recievedStockRepository) {
        this.stockRepository = recievedStockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(final String id) {
        return stockRepository.findById(id);
    }

    public Stock createStock(final RequestStockDTO requestStockDTO) {
        Stock newStock = new Stock(requestStockDTO);
        validateRequestStockDTO(requestStockDTO);
        return stockRepository.save(newStock);
    }

    public Optional<Stock> updateStock(final String id, final Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.getSymbol());
                    stock.setCompanyName(updatedStock.getCompanyName());
                    double newPrice = stock.changePrice(updatedStock.getPrice(), true);
                    stock.setPrice(newPrice);

                    return stockRepository.save(stock);
                });
    }

    public void deleteStock(final String id) {
        stockRepository.deleteById(id);
    }

    public static void validateRequestStockDTO(final RequestStockDTO requestStockDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(requestStockDTO);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }

            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }

    public void validateAndCreateStock(final RequestStockDTO requestStockDTO) {
        validateRequestStockDTO(requestStockDTO);
        Stock newStock = new Stock(requestStockDTO);
        stockRepository.save(newStock);
    }
}
