package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.service.StockService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public final class StockController {

    /**
     * Serviço de estoque para manipulação de stocks.
     */
    private final StockService stockService;

    /**
     * Construtor para injetar StockService.
     *
     * @param stockService O serviço de estoque.
     */
    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Retorna todos os stocks.
     *
     * @return A lista de todos os stocks.
     */
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * Retorna um stock pelo ID.
     *
     * @param id O ID do stock.
     * @return O ResponseEntity contendo o stock ou status de not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable final String id) {
        Optional<Stock> responseStock = stockService.getStockById(id);
        return responseStock.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo stock.
     *
     * @param data Os dados do stock.
     * @return O ResponseEntity contendo o stock criado.
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody final RequestStockDTO data) {
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    /**
     * Atualiza um stock existente.
     *
     * @param id           O ID do stock.
     * @param updatedStock O stock atualizado.
     * @return O ResponseEntity contendo o stock atualizado ou status de not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable final String id, @RequestBody final Stock updatedStock) {
        Optional<Stock> responseStock = stockService.updateStock(id, updatedStock);
        if (responseStock.isPresent()) {
            return ResponseEntity.ok(responseStock.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um stock.
     *
     * @param id O ID do stock a ser excluído.
     * @return O ResponseEntity sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable final String id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
