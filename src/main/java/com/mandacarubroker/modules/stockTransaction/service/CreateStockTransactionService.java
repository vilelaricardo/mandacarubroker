package com.mandacarubroker.modules.stockTransaction.service;

import com.mandacarubroker.exception.StockTransactionFoundException;
import com.mandacarubroker.modules.stockTransaction.StockTransaction;
import com.mandacarubroker.modules.stockTransaction.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public StockTransaction execute(StockTransaction stockTransaction) {

        this.stockTransactionRepository
                .findByStockIdOrUserId(stockTransaction.getUserId(), stockTransaction.getStockId())
                .ifPresent((stockTransactionAux) -> {
                    throw new StockTransactionFoundException();
                });

        return this.stockTransactionRepository.save(stockTransaction);

    }
}