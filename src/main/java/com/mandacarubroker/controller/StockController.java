package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
import java.util.Optional;

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
    public ResponseEntity<Stock> getStockById(@PathVariable final String id) {
        Optional<Stock> stock = stockService.getStockById(id);

        if (stock.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@Valid @RequestBody final RequestStockDTO requestStockDTO) {
        Stock createdStock = stockService.createStock(requestStockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable final String id, @Valid @RequestBody final RequestStockDTO updatedStockDTO) {
        Optional<Stock> updatedStock = stockService.updateStock(id, updatedStockDTO);

        if (updatedStock.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedStock.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStock(@PathVariable final String id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
