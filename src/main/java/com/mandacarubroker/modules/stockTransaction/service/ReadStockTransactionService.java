package com.mandacarubroker.modules.stockTransaction.service;

import com.mandacarubroker.exception.StockTransactionNotFoundException;
import com.mandacarubroker.modules.stockTransaction.StockTransaction;
import com.mandacarubroker.modules.stockTransaction.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadStockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public List<StockTransaction> findAll() {
        return stockTransactionRepository.findAll();
    }

    public StockTransaction findById(String transactionId) {
        Optional<StockTransaction> optionalStockTransaction = stockTransactionRepository.findById(transactionId);

        if (optionalStockTransaction.isEmpty()) {
            throw new StockTransactionNotFoundException();
        }
        return optionalStockTransaction.get();
    }

    // Sugestões de melhoria:
    // - findByStockId(String stockId)
    // - findByUserId(String userId)
}