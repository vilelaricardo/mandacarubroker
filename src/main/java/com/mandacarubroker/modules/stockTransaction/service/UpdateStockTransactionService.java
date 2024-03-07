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
public class UpdateStockTransactionService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public StockTransaction updateStockTransaction(String transactionId, StockTransaction updatedStockTransaction) throws Exception {
        Optional<StockTransaction> stockTransactionOptional = stockTransactionRepository.findById(transactionId);
        if (stockTransactionOptional.isPresent()) {
            StockTransaction stockTransaction = stockTransactionOptional.get();

            // Atualiza os dados da transação
            stockTransaction.setQuantity(updatedStockTransaction.getQuantity());

            // Verifica se o Stock associado existe
            Optional<Stock> stockOptional = stockRepository.findById(stockTransaction.getStockId());
            if (!stockOptional.isPresent()) {
                throw new StockNotFoundException();
            }

            // Salva a transação atualizada
            return stockTransactionRepository.save(stockTransaction);
        } else {
            throw new StockTransactionNotFoundException();
        }
    }
}