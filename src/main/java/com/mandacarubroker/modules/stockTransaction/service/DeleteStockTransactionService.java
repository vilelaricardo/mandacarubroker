package com.mandacarubroker.modules.stockTransaction.service;

import com.mandacarubroker.exception.StockNotFoundException;
import com.mandacarubroker.exception.StockTransactionNotFoundException;
import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stockTransaction.StockTransaction;
import com.mandacarubroker.modules.stock.StockRepository;
import com.mandacarubroker.modules.stockTransaction.StockTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DeleteStockTransactionService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public void deleteStockTransaction(String transactionId) throws Exception {
        Optional<StockTransaction> stockTransactionOptional = stockTransactionRepository.findById(transactionId);
        if (stockTransactionOptional.isPresent()) {
            StockTransaction stockTransaction = stockTransactionOptional.get();
            String stockId = stockTransaction.getStockId();

            // Verifica se o Stock associado existe
            Optional<Stock> stockOptional = stockRepository.findById(stockId);
            if (!stockOptional.isPresent()) {
                throw new StockNotFoundException();
            }

            // Deleta a transação
            stockTransactionRepository.delete(stockTransaction);
        } else {
            throw new StockTransactionNotFoundException();
        }
    }
}