package com.mandacarubroker.modules.stock.service;

import com.mandacarubroker.exception.StockNotFoundException;
import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.StockRepository;
import com.mandacarubroker.modules.stockTransaction.StockTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DeleteStockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public void deleteStock(String stockId) {
        Optional<Stock> stockOptional = this.stockRepository.findById(stockId);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            // Deleta as transações associadas ao Stock
            stockTransactionRepository.deleteByStock(stock);
            // Deleta o Stock
            this.stockRepository.delete(stock);
        } else {
            throw new StockNotFoundException();
        }
    }
}