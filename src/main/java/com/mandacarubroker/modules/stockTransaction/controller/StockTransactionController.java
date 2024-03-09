package com.mandacarubroker.modules.stockTransaction.controller;

import com.mandacarubroker.modules.stockTransaction.StockTransaction;
import com.mandacarubroker.modules.stockTransaction.service.CreateStockTransactionService;
import com.mandacarubroker.modules.stockTransaction.service.DeleteStockTransactionService;
import com.mandacarubroker.modules.stockTransaction.service.ReadStockTransactionService;
import com.mandacarubroker.modules.stockTransaction.service.UpdateStockTransactionService;
import jakarta.validation.Valid;

import java.util.List;

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
    public ResponseEntity<Object> findTransactionById(@PathVariable("transactionId") String transactionId) throws Exception {
        try {
            this.readStockTransactionService.findById(transactionId);
            return ResponseEntity.ok().body(readStockTransactionService.findById(transactionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Cria uma nova transação.
     *
     * @param stockTransaction dados da nova transação.
     * @return Transação criada.
     */
    @PostMapping("/") // Vínculo do método "createStockTransaction" com o método "HTTP - POST"
    public ResponseEntity<Object> createStockTransaction(@Valid @RequestBody StockTransaction stockTransaction) throws Exception {
        try {
            var result = this.createStockTransactionService.execute(stockTransaction);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Atualiza uma transação.
     *
     * @param transactionId           ID da transação.
     * @param updatedStockTransaction Transação com os dados atualizados.
     * @return Transação atualizada.
     */
    @PutMapping("/{transactionId}")
    public ResponseEntity<Object> updateStockTransaction(@PathVariable("transactionId") String transactionId,
                                                         @Valid @RequestBody StockTransaction updatedStockTransaction) {
        try {
            this.updateStockTransactionService.updateStockTransaction(transactionId, updatedStockTransaction);
            return ResponseEntity.ok().body("Transação atualizada com sucesso!");
        } catch (Exception e) {
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
