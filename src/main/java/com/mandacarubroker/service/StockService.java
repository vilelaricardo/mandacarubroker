package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private static final String NOT_FOUND_MSG = "Stock Not Found";

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(String id) {
        return stockRepository.findById(id).orElseThrow(()->new EntityNotFoundException(NOT_FOUND_MSG));
    }

    public Stock createStock(RequestStockDTO data) {
        validateRequestStockDTO(data);
        Stock newStock = new Stock(data);
        return stockRepository.save(newStock);
    }

    public Stock updateStock(String id, RequestStockDTO updatedStock) {
        validateRequestStockDTO(updatedStock);
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.symbol());
                    stock.setCompanyName(updatedStock.companyName());
                    double newPrice = stock.changePrice(updatedStock.price(),true);
                    stock.setPrice(newPrice);
                    return stockRepository.save(stock);
                }).orElseThrow(()->new EntityNotFoundException(NOT_FOUND_MSG));
    }

    public void deleteStock(String id) {
        if (!stockRepository.existsById(id)){
            throw new EntityNotFoundException(NOT_FOUND_MSG);
        }
        stockRepository.deleteById(id);
    }

    public static void validateRequestStockDTO(RequestStockDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<RequestStockDTO>> violations = factory.getValidator().validate(data);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");
            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }
            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
            factory.close();
            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
        factory.close();
    }
}
