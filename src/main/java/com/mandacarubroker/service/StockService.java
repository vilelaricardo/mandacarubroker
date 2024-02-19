package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StockService {

    /**
     * Repository responsible for CRUD operations on Stock entities.
     */
    private final StockRepository stockRepository;

    /**
     * Constructor for StockService.
     *
     * @param stockRepository The StockRepository object
     */
    public StockService(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Retrieves all stocks stored in the database.
     *
     * @return List of stocks
     */
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Retrieves a stock from the database by its unique identifier.
     *
     * @param id The ID of the stock
     * @return Optional containing the stock, if found
     */
    public Optional<Stock> getStockById(final String id) {
        return stockRepository.findById(id);
    }

    /**
     * Creates a new stock entry in the database.
     *
     * @param data The RequestStockDTO object containing the data for the new stock
     * @return The created stock
     */
    public Stock createStock(final RequestStockDTO data) {
        Stock novaAcao = new Stock(data);
        validateRequestStockDTO(data);
        return stockRepository.save(novaAcao);
    }

    /**
     * Updates an existing stock entry in the database.
     *
     * @param id           The ID of the stock to be updated
     * @param updatedStock The updated stock object
     * @return Optional containing the updated stock, if found
     */
    public Optional<Stock> updateStock(final String id, final Stock updatedStock) {
        return stockRepository.findById(id)
                .map(stock -> {
                    stock.setSymbol(updatedStock.getSymbol());
                    stock.setCompanyName(updatedStock.getCompanyName());
                    stock.setPrice(updatedStock.getPrice());

                    return stockRepository.save(stock);
                });
    }

    /**
     * Deletes a stock entry from the database by its ID.
     *
     * @param id The ID of the stock to delete
     */
    public void deleteStock(final String id) {
        stockRepository.deleteById(id);
    }

    /**
     * Validates the data of the RequestStockDTO object.
     *
     * @param data The RequestStockDTO object to validate
     */
    public static void validateRequestStockDTO(final RequestStockDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder mensagemDeErro = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                mensagemDeErro.append(
                        String.format("[%s: %s], ",
                                violation.getPropertyPath(), violation.getMessage()
                        )
                );
            }
            mensagemDeErro.delete(mensagemDeErro.length() - 2, mensagemDeErro.length());
            throw new ConstraintViolationException(mensagemDeErro.toString(), violations);
        }
    }

    /**
     * Validates the RequestStockDTO object and creates a new stock entry in the database.
     *
     * @param data The RequestStockDTO object containing the data for the new stock
     */
    public void validateAndCreateStock(final RequestStockDTO data) {
        validateRequestStockDTO(data);

        Stock novaAcao = new Stock(data);
        stockRepository.save(novaAcao);
    }
}
