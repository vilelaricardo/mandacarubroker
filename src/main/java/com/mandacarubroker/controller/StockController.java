package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
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

/**
 * Controller para gerenciar ações.
 */
@RestController
@RequestMapping("/stocks")
public class StockController {

  private final StockService stockService;

  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  /**
   * Obtém todas as ações existentes.
   *
   * @return Lista de ações.
   */
  @GetMapping
  public List<Stock> getAllStocks() {
    return stockService.getAllStocks();
  }

  /**
   * Obtém uma ação pelo seu ID.
   *
   * @param id ID da ação.
   * @return Ação com o ID especificado.
   */
  @GetMapping("/{id}")
  public Stock getStockById(@PathVariable String id) {
    return stockService.getStockById(id).orElse(null);
  }

  /**
   * Cria uma nova ação.
   *
   * @param data DTO com os dados da nova ação.
   * @return Ação criada.
   */
  @PostMapping
  public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data) {
    Stock createdStock = stockService.createStock(data);
    return ResponseEntity.ok(createdStock);
  }

  /**
   * Atualiza uma ação.
   *
   * @param id ID da ação.
   * @param updatedStock Ação com os dados atualizados.
   * @return Ação atualizada.
   */
  @PutMapping("/{id}")
  public Stock updateStock(@PathVariable String id, @RequestBody Stock updatedStock) {
    return stockService.updateStock(id, updatedStock).orElse(null);
  }

  /**
   * Exclui uma ação.
   *
   * @param id ID da ação.
   */
  @DeleteMapping("/{id}")
  public void deleteStock(@PathVariable String id) {
    stockService.deleteStock(id);
  }
}