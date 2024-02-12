package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mandacarubroker.validation.RecordValidation.validateRequestDTO;

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

    public Stock createStock(final RequestStockDTO requestStockDTO) {
        validateRequestDTO(requestStockDTO);
        Stock newStock = new Stock(requestStockDTO);
        return stockRepository.save(newStock);
    }

    public Optional<Stock> updateStock(final String stockId, final RequestStockDTO requestStockDTO) {
        validateRequestDTO(requestStockDTO);
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
}
