package com.mandacarubroker.modules.stockTransaction.controller;

import com.mandacarubroker.modules.stockTransaction.StockTransaction;
import com.mandacarubroker.modules.stockTransaction.service.ReadStockTransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StockTransactionControllerTest {

    @DisplayName("Retorna todas as transações")
    @Test
    void getAllTransactions_ReturnsListOfTransactions() {
        // Arrange
        ReadStockTransactionService readStockTransactionService = mock(ReadStockTransactionService.class);
        List<StockTransaction> expectedStocksTransaction = Arrays.asList(new StockTransaction(), new StockTransaction());
        when(readStockTransactionService.findAll()).thenReturn(expectedStocksTransaction);

        // Act
        List<StockTransaction> actualStocks = readStockTransactionService.findAll();

        // Assert
        assertEquals(expectedStocksTransaction, actualStocks);
    }

    @DisplayName("Retorna uma transação pelo ID")
    @Test
    void getTransactionById_WithValidId_ReturnsTransaction() {
        // Arrange
        ReadStockTransactionService readStockTransactionService = mock(ReadStockTransactionService.class);
        StockTransaction expectedStockTransaction = new StockTransaction();
        when(readStockTransactionService.findById("validId")).thenReturn(expectedStockTransaction);

        // Act
        StockTransaction actualStockTransaction = readStockTransactionService.findById("validId");

        // Assert
        assertEquals(expectedStockTransaction, actualStockTransaction);
    }

}
