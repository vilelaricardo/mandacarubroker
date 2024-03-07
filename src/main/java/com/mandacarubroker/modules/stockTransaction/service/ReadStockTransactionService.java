package com.mandacarubroker.modules.stockTransaction.service;

import com.mandacarubroker.modules.stockTransaction.StockTransaction;
import com.mandacarubroker.modules.stockTransaction.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadStockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public List<StockTransaction> findAll() {
        return stockTransactionRepository.findAll();
    }

    public StockTransaction findById(String transactionId) {
        return stockTransactionRepository.findById(transactionId).orElse(null);
    }

    // Sugestões de melhoria:
    // - findByStockId(String stockId)
    // - findByUserId(String userId)
}