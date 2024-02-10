package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;

import jakarta.validation.*;
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

    public Optional<Stock> getStockById(final String stockId) {
        return stockRepository.findById(stockId);
    }

    public Stock createStock(@Valid final RequestStockDTO requestStockDTO) {
        validateRequestStockDTO(requestStockDTO);
        Stock newStock = new Stock(requestStockDTO);
        return stockRepository.save(newStock);
    }

    public Optional<Stock> updateStock(final String stockId, final RequestStockDTO requestStockDTO) {
        validateRequestStockDTO(requestStockDTO);
        return stockRepository.findById(stockId)
                .map(stock -> {
                    stock.setSymbol(requestStockDTO.symbol());
                    stock.setCompanyName(requestStockDTO.companyName());
                    stock.setPrice(requestStockDTO.price());
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
}
