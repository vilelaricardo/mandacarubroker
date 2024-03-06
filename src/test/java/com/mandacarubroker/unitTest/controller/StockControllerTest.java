package com.mandacarubroker.unitTest.controller;

import com.mandacarubroker.controller.StockController;
import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.repository.StockRepository;
import com.mandacarubroker.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StockControllerTest {
  @Mock
  private StockService stockService;

  @InjectMocks
  private StockController stockController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  private final Stock stock = new Stock("1", "Test4", "Test s.a", 150.00, 1);
  private final Stock updatedStockData =  new Stock("1", "TEST4", "Test s.a", 200.00, 2);

  @Test
  void testGetAllStocks() {
    List<Stock> expectedStocks = new ArrayList<>();
    expectedStocks.add(stock);
    expectedStocks.add(new Stock("2", "GOOG3", "Google s.a", 500.00, 3));
    when(stockService.getAllStocks()).thenReturn(expectedStocks);

    List<Stock> actualStocks = stockController.getAllStocks();

    assertEquals(expectedStocks, actualStocks);
  }

  @Test
  void testGetStockById() {
    Stock expectedStock = stock;
    when(stockService.getStockById("1")).thenReturn(Optional.of(expectedStock));

    Stock actualStock = stockController.getStockById("1");

    assertEquals(expectedStock, actualStock);
  }

  @Test
  void testCreateStock() {
    RequestStockDataTransferObject requestData = new RequestStockDataTransferObject(
        "Test4",
        "Test s.a",
        150.00,
        4
    );

    Stock expectedStock = stock;
    when(stockService.createStock(requestData)).thenReturn(expectedStock);


    ResponseEntity<Stock> responseEntity = stockController.createStock(requestData);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedStock, responseEntity.getBody());
  }

  @Test
  void testUpdateStock() {
    String id = "1";
    when(stockService.updateStock(id, updatedStockData)).thenReturn(Optional.of(updatedStockData));

    Stock actualStock = stockController.updateStock(id, updatedStockData);

    assertEquals(updatedStockData, actualStock);
  }

  @Test
  void testDeleteStock() {
    String id = "1";

    stockController.deleteStock(id);

    verify(stockService, times(1)).deleteStock(id);
  }
}
