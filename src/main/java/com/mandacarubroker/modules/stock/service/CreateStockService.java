package com.mandacarubroker.modules.stock.service;

import com.mandacarubroker.exception.StockFoundException;
import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock execute(Stock stock) {

        // Variável para verificar se o símbolo da ação existe
        this.stockRepository
                .findBySymbol(stock.getSymbol())
                .ifPresent((stockAux) -> {
                    throw new StockFoundException();
                });
        return this.stockRepository.save(stock);

    }
}
