package com.mandacarubroker.modules.stock.controller;

import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.service.CreateStockService;
import com.mandacarubroker.modules.stock.service.DeleteStockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciar ações.
 */
@RestController
@RequestMapping("/stocks")
public class StockController {

    // Vínculo CONTROLLER com SERVICES
    @Autowired
    private CreateStockService createStockService;
    @Autowired
    private DeleteStockService deleteStockService;


    /**
     * Cria uma nova ação.
     *
     * @param stock dados da nova ação.
     * @return Açãoo criada.
     */
    @PostMapping("/") // Vínculo do método "createStock" com o método "HTTP - POST"
    public ResponseEntity<Object> createStock(@Valid @RequestBody Stock stock) {

        try {
            var result = this.createStockService.execute(stock);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    /**
     * Deleta uma ação.
     *
     * @param stockId identificador da ação
     * @return Ação deletada.
     */
    @DeleteMapping("/{id}") // Método para deletar Stock
    public ResponseEntity<Object> deleteStock(@PathVariable("id") String stockId) {
        try {
            this.deleteStockService.deleteStock(stockId);
            return ResponseEntity.ok().body("Ação deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
