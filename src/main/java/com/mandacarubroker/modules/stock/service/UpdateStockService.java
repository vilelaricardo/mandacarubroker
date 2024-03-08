package com.mandacarubroker.modules.stock.service;

import com.mandacarubroker.exception.StockNotFoundException;
import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateStockService {
    @Autowired
    private StockRepository stockRepository;

    public Stock updateStock(String stockId, Stock updatedStock) throws Exception {
        // Busca da ação pelo ID
        Stock stockOptional = stockRepository.findById(stockId).orElseThrow(() -> new StockNotFoundException());

        // Atualiza os dados da ação
        stockOptional.setSymbol(updatedStock.getSymbol());
        stockOptional.setCompanyName(updatedStock.getCompanyName());
        stockOptional.setPrice(updatedStock.getPrice());
        stockOptional.setQuantity(updatedStock.getQuantity());


        // Salva dados da ação atualizada
        return stockRepository.save(stockOptional);
    }
}
