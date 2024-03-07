package com.mandacarubroker.modules.stockTransaction.controller;

import com.mandacarubroker.modules.stockTransaction.StockTransaction;
import com.mandacarubroker.modules.stockTransaction.service.CreateStockTransactionService;
import com.mandacarubroker.modules.stockTransaction.service.DeleteStockTransactionService;
import com.mandacarubroker.modules.stockTransaction.service.ReadStockTransactionService;
import com.mandacarubroker.modules.stockTransaction.service.UpdateStockTransactionService;
import com.sun.jdi.event.ExceptionEvent;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciar transações.
 */
@RestController
@RequestMapping("/transaction")
public class StockTransactionController {

    // Vínculo CONTROLLER com SERVICES
    @Autowired
    private CreateStockTransactionService createStockTransactionService;
    @Autowired
    private DeleteStockTransactionService deleteStockTransactionService;
    @Autowired
    private ReadStockTransactionService readStockTransactionService;
    @Autowired
    private UpdateStockTransactionService updateStockTransactionService;


    /**
     * Recupera todas as transações.
     *
     * @return lista de todas as transações
     */
    @GetMapping("/")
    public List<StockTransaction> findAllTransactions() {
        return readStockTransactionService.findAll();
    }


    /**
     * Recupera uma transação pelo ID.
     *
     * @param transactionId ID da transação
     * @return Transação encontrada, ou Optional.empty() se não encontrada
     */
    @GetMapping("/{transactionId}")
    public StockTransaction findTransactionById(@PathVariable("transactionId") String transactionId) {
        return readStockTransactionService.findById(transactionId);
    }


    /**
     * Cria uma nova transação.
     *
     * @param stockTransaction dados da nova transação.
     * @return Transação criada.
     */
    @PostMapping("/") // Vínculo do método "createStockTransaction" com o método "HTTP - POST"
    public StockTransaction createStockTransaction(@Valid @RequestBody StockTransaction stockTransaction) {
        return this.createStockTransactionService.execute(stockTransaction);
    }


    /**
     * Atualiza uma transação.
     *
     * @param transactionId          ID da transação.
     * @param updatedStockTransaction Transação com os dados atualizados.
     * @return Transação atualizada.
     */
    @PutMapping("/{transactionId}")
    public ResponseEntity<Object> updateStockTransaction(@PathVariable("transactionId") String transactionId,
                                                         @Valid @RequestBody StockTransaction updatedStockTransaction){
        try{
            this.updateStockTransactionService.updateStockTransaction(transactionId, updatedStockTransaction);
            return ResponseEntity.ok().body("Transação atualizada com sucesso!");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Deleta uma transação.
     *
     * @param transactionId identificador da transação
     * @return Transação deletada.
     */
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Object> deleteStockTransaction(@PathVariable("transactionId") String transactionId) {
        try {
            this.deleteStockTransactionService.deleteStockTransaction(transactionId);
            return ResponseEntity.ok().body("Transação deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
