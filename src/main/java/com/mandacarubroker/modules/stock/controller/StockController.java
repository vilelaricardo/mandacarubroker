package com.mandacarubroker.modules.stock.controller;

import com.mandacarubroker.exception.StockNotFoundException;
import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.service.CreateStockService;
import com.mandacarubroker.modules.stock.service.DeleteStockService;
import com.mandacarubroker.modules.stock.service.ReadStockService;
import com.mandacarubroker.modules.stock.service.UpdateStockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Vínculo CONTROLLER com SERVICES
    @Autowired
    private CreateStockService createStockService;
    @Autowired
    private DeleteStockService deleteStockService;
    @Autowired
    private ReadStockService readStockService;
    @Autowired
    private UpdateStockService updateStockService;

    /**
     * Recupera todas as ações
     *
     * @return lista de todas as ações
     */
    @GetMapping("/")
    public ResponseEntity<Object> findAllStocks() {
        return ResponseEntity.ok(readStockService.findAll());
    }


    /**
     * Recupera uma ação pelo ID.
     *
     * @param stockId ID da ação
     * @return Ação encontrada
     */
    @GetMapping("/{stockId}")
    public ResponseEntity<Object> findStockById(@PathVariable("stockId") String stockId) throws Exception {
        try{
            this.readStockService.findById(stockId);
            return ResponseEntity.ok().body(readStockService.findById(stockId));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    /**
     * Cria uma nova ação
     *
     * @param stock dados da nova ação
     * @return Açãoo criada
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
     * Atualiza uma ação
     *
     * @param stockId      ID da ação
     * @param updatedStock Dados atualizados da ação
     * @return Ação atualizada
     * @throws StockNotFoundException
     */
    @PutMapping("/{stockId}")
    public ResponseEntity<Object> updateStock(@PathVariable("stockId") String stockId,
                                             @Valid @RequestBody Stock updatedStock) throws Exception {
        try {
            Stock stockUpdated = updateStockService.updateStock(stockId, updatedStock);
            return ResponseEntity.ok().body(stockUpdated);
        } catch (StockNotFoundException e) {
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
