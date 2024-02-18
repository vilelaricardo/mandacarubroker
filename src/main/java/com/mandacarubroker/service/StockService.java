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
     * Repositório para gerenciar dados de ações.
     */
    private final StockRepository stockRepository;

    /**
     * Construtor da classe StockService.
     *
     * @param stockRepository O repositório de ações a ser injetado no serviço.
     */
    public StockService(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Recupera todas as ações.
     *
     * @return Lista de todas as ações.
     */
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Recupera uma ação pelo seu ID.
     *
     * @param id O ID da ação.
     * @return Optional contendo a ação, ou vazio se não encontrada.
     */
    public Optional<Stock> getStockById(final String id) {
        return stockRepository.findById(id);
    }

    /**
     * Cria uma nova ação.
     *
     * @param data Os dados para a nova ação.
     * @return A ação criada.
     */
    public Stock createStock(final RequestStockDTO data) {
        Stock novaAcao = new Stock(data);
        validateRequestStockDTO(data);
        return stockRepository.save(novaAcao);
    }

    /**
     * Atualiza uma ação existente.
     *
     * @param id           O ID da ação a ser atualizada.
     * @param updatedStock Os dados atualizados da ação.
     * @return Optional contendo a ação atualizada, ou vazio se não encontrada.
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
     * Exclui uma ação pelo seu ID.
     *
     * @param id O ID da ação a ser excluída.
     */
    public void deleteStock(final String id) {
        stockRepository.deleteById(id);
    }

    /**
     * Valida um objeto RequestStockDTO.
     *
     * @param data O objeto RequestStockDTO a ser validado.
     */
    public static void validateRequestStockDTO(final RequestStockDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Falha na validação. Detalhes: ");

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
     * @param data Os dados para a nova ação.
     */
    public void validateAndCreateStock(final RequestStockDTO data) {
        validateRequestStockDTO(data);
        Stock novaAcao = new Stock(data);
        stockRepository.save(novaAcao);
    }
}
