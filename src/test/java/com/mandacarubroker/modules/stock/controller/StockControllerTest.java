package com.mandacarubroker.modules.stock.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.service.CreateStockService;
import com.mandacarubroker.modules.stock.service.DeleteStockService;
import com.mandacarubroker.modules.stock.service.ReadStockService;
import com.mandacarubroker.modules.stock.service.UpdateStockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class StockControllerTest {

    @Autowired
    private StockController stockController;
    @MockBean
    private ReadStockService readStockService;

    @Test
    @DisplayName("Retorna todas as ações no get")
    void getAllStocks_ReturnsListOfStocks() {
        // Arrange
        List<Stock> expectedStocks = Arrays.asList(new Stock(), new Stock());
        when(readStockService.findAll()).thenReturn(expectedStocks);

        // Act
        ResponseEntity<Object> responseEntity = stockController.findAllStocks();
        List<Stock> actualStocks = (List<Stock>) responseEntity.getBody();

        // Assert
        assertEquals(expectedStocks, actualStocks);
    }

    @DisplayName("Retorna uma ação através de um Id válido no get")
    @Test
    void getStockById_WithValidId_ReturnsStock() throws Exception {
        // Arrange
        Stock expectedStock = new Stock();
        when(readStockService.findById("validId")).thenReturn(expectedStock);

        // Act
        ResponseEntity<Object> actualStockResponse = stockController.findStockById("validId");
        Stock actualStock = (Stock) actualStockResponse.getBody();

        // Assert
        assertEquals(expectedStock, actualStock);
    }

    @MockBean
    private CreateStockService createStockService;

    @DisplayName("Cria uma ação quando os dados estiverem corretos")
    @Test
    void createStock_WithValidData_ReturnsCreatedStock() {
        // Arrange
        Stock expectedStock = new Stock();
        when(createStockService.execute(expectedStock)).thenReturn(expectedStock);

        // Act
        ResponseEntity<Object> actualStockResponse = stockController.createStock(expectedStock);
        Stock actualStock = (Stock) actualStockResponse.getBody();

        // Assert
        assertEquals(expectedStock, actualStock);
    }

    @MockBean
    private UpdateStockService updateStockService;

    @DisplayName("Atualiza uma ação e retorna a ação atualizada")
    @Test
    void updateStock_WithValidData_ReturnsUpdatedStock() throws Exception {
        // Arrange
        String stockId = "validId";
        Stock updatedStockData = new Stock();
        Stock expectedUpdatedStock = new Stock();
        when(updateStockService.updateStock(stockId, updatedStockData)).thenReturn(expectedUpdatedStock);

        // Act
        ResponseEntity<Object> actualUpdatedStock = stockController.updateStock(stockId, updatedStockData);
        Stock actualStock = (Stock) actualUpdatedStock.getBody();

        // Assert
        assertEquals(expectedUpdatedStock, actualStock);
    }

    @MockBean
    private DeleteStockService deleteStockService;

    @DisplayName("Deleta uma ação por ID")
    @Test
    void deleteStock_DeletesStockById() throws Exception {
        // Arrange
        String stockId = "validId";

        // Act
        deleteStockService.deleteStock(stockId);

        // Assert
        verify(deleteStockService, times(1)).deleteStock(stockId);
    }
}