package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.dtos.RequestStockDTO;
import com.mandacarubroker.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable String id) {
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data, HttpServletRequest http) {
        Stock createdStock = stockService.createStock(data);
        URI uri = UriComponentsBuilder.fromUriString(http.getRequestURI()).path("/{id}").buildAndExpand(createdStock.getId()).toUri();
        return ResponseEntity.created(uri).body(createdStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable String id, @RequestBody RequestStockDTO updatedStock) {
        return ResponseEntity.ok(stockService.updateStock(id, updatedStock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable String id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

}
