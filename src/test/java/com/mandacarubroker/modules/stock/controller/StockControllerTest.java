package com.mandacarubroker.modules.stock.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class StockControllerTest {

    @DisplayName("Retorna todas as ações no get")
    @Test
    void getAllStocks_ReturnsListOfStocks() {
        // Arrange
        StockService stockService = mock(StockService.class);
        List<Stock> expectedStocks = Arrays.asList(new Stock(), new Stock());
        when(stockService.getAllStocks()).thenReturn(expectedStocks);

        StockController stockController = new StockController(stockService);

        // Act
        List<Stock> actualStocks = stockController.getAllStocks();

        // Assert
        assertEquals(expectedStocks, actualStocks);
    }

    @DisplayName("Retorna uma ação através de um Id válido no get")
    @Test
    void getStockById_WithValidId_ReturnsStock() {
        // Arrange
        StockService stockService = mock(StockService.class);
        Stock expectedStock = new Stock();
        when(stockService.getStockById("validId")).thenReturn(Optional.of(expectedStock));

        StockController stockController = new StockController(stockService);

        // Act
        Stock actualStock = stockController.getStockById("validId");

        // Assert
        assertEquals(expectedStock, actualStock);
    }
    @DisplayName("Cria uma ação quand os dados estiverem corretos")
    @Test
    void createStock_WithValidData_ReturnsCreatedStock() {
        // Arrange
        StockService stockService = mock(StockService.class);
        RequestStockDTO requestData = new RequestStockDTO("BB3", "Banco do Brasil S.A.", 56.90);
        Stock expectedStock = new Stock(requestData);
        when(stockService.createStock(requestData)).thenReturn(expectedStock);

        StockController stockController = new StockController(stockService);

        // Act
        ResponseEntity<Stock> responseEntity = stockController.createStock(requestData);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStock, responseEntity.getBody());
    }

    @DisplayName("Atualiza uma ação e retorna a ação atualizada")
    @Test
    void updateStock_WithValidData_ReturnsUpdatedStock() {
        // Arrange
        StockService stockService = mock(StockService.class);
        String stockId = "validId";
        RequestStockDTO updatedData = new RequestStockDTO("BB4", "Banco do Brasil S.A.", 60.00);
        RequestStockDTO expectedData = new RequestStockDTO("BB4", "Banco do Brasil S.A.", 60.00);
        Stock updatedStockData = new Stock(updatedData); // Provide valid updated data
        Stock expectedUpdatedStock = new Stock(expectedData); // Mock the expected updated stock
        when(stockService.updateStock(stockId, updatedStockData)).thenReturn(Optional.of(expectedUpdatedStock));

        StockController stockController = new StockController(stockService);

        // Act
        Stock actualUpdatedStock = stockController.updateStock(stockId, updatedStockData);

        // Assert
        assertEquals(expectedUpdatedStock, actualUpdatedStock);
    }

    @DisplayName("Deleta uma ação pelo Id quando o id for válido")
    @Test
    void deleteStock_WithValidId_DeletesStock() {
        // Arrange
        StockService stockService = mock(StockService.class);
        String stockId = "validId";

        StockController stockController = new StockController(stockService);

        // Act
        stockController.deleteStock(stockId);

        // Assert
        verify(stockService, times(1)).deleteStock(stockId);
    }

    @DisplayName("Retorna null ao buscar uma ação com Id inválido no get")
    @Test
    void getStockById_WithInvalidId_ReturnsNull() {
        // Arrange
        StockService stockService = mock(StockService.class);
        when(stockService.getStockById("invalidId")).thenReturn(Optional.empty());

        StockController stockController = new StockController(stockService);

        // Act
        Stock actualStock = stockController.getStockById("invalidId");

        // Assert
        assertNull(actualStock);
    }

}
