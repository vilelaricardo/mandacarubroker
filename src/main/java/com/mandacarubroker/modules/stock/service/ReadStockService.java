package com.mandacarubroker.modules.stock.service;

import com.mandacarubroker.exception.StockNotFoundException;
import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadStockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }


    public Stock findById(String stockId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isEmpty()) {
            throw new StockNotFoundException();
        }
        return optionalStock.get();
    }
}