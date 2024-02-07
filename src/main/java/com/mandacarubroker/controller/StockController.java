package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(final StockService receivedStockService) {
        this.stockService = receivedStockService;
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable final String id) {
        return stockService.getStockById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody final RequestStockDTO requestStockDTO) {
        Stock createdStock = stockService.createStock(requestStockDTO);
        return ResponseEntity.ok(createdStock);
    }

    @PutMapping("/{id}")
    public Stock updateStock(@PathVariable final String id, @RequestBody final Stock updatedStock) {
        return stockService.updateStock(id, updatedStock).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable final String id) {
        stockService.deleteStock(id);
    }
}
