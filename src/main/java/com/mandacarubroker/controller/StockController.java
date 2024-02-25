package com.mandacarubroker.controller;


import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* The Stock controller class.
**/
@RestController
@RequestMapping("/stocks")
public class StockController {

  private final StockService stockService;

  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @Operation(summary = "Get all stock registers", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success"),
  })
  @GetMapping
  public List<Stock> getAllStocks() {
    return stockService.getAllStocks();
  }

  @Operation(summary = "Get specific stock", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success"),
  })
  @GetMapping("/{id}")
  public Stock getStockById(@PathVariable String id) {
    return stockService.getStockById(id).orElse(null);
  }

  @Operation(summary = "Register a stock", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success"),
  })
  @PostMapping
  public ResponseEntity<Stock> createStock(@RequestBody RequestStockDataTransferObject data) {
    Stock createdStock = stockService.createStock(data);
    return ResponseEntity.ok(createdStock);
  }

  @Operation(summary = "Update a stock", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success"),
  })
  @PutMapping("/{id}")
  public Stock updateStock(@PathVariable String id, @RequestBody Stock updatedStock) {
    return stockService.updateStock(id, updatedStock).orElse(null);
  }

  @Operation(summary = "Delete a stock", method = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success"),
  })
  @DeleteMapping("/{id}")
  public void deleteStock(@PathVariable String id) {
    stockService.deleteStock(id);
  }
}
