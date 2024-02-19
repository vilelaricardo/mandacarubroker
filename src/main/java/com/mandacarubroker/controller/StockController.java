package com.mandacarubroker.controller;

import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;
import java.util.Optional;

/**
 * Controlador para manipulação de estoques.
 */
@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    /**
     * Construtor do controlador.
     *
     * @param stockService Serviço de estoque
     */
    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Retorna todos os estoques.
     *
     * @return Lista de estoques
     */
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * Retorna um estoque pelo seu ID.
     *
     * @param id ID do estoque a ser retornado
     * @return O estoque correspondente ao ID, se encontrado; caso contrário, null
     */
    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable final String id) {
        return stockService.getStockById(id).orElse(null);
    }

    /**
     * Cria um novo estoque.
     *
     * @param data Dados do novo estoque
     * @return ResponseEntity contendo o novo estoque
     */
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody final RequestStockDTO data) {
        final Stock createdStock = stockService.createStock(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    /**
     * Atualiza um estoque existente.
     *
     * @param id           ID do estoque a ser atualizado
     * @param updatedStock Estoque atualizado
     * @return O estoque atualizado, se a atualização for bem-sucedida; caso contrário, null
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
     * Exclui um estoque.
     *
     * @param id ID do estoque a ser excluído
     */
    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable final String id) {
        stockService.deleteStock(id);
    }

}
