package com.mandacarubroker.unitTest.service;

import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import com.mandacarubroker.service.StockService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StockServiceTest {

  @Mock
  private StockRepository stockRepository;

  private StockService stockService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    stockService = new StockService(stockRepository);
  }

  @Test
  void createStock_ValidData_ReturnsStock() {
    RequestStockDataTransferObject data = new RequestStockDataTransferObject("ABCD1", "Company Name", 10.5);

    Stock savedStock = new Stock(data);
    when(stockRepository.save(any())).thenReturn(savedStock);

    Stock createdStock = stockService.createStock(data);
    assertNotNull(createdStock);
    assertEquals(createdStock.getSymbol(), data.symbol());
    assertEquals(createdStock.getPrice(), data.price());
    assertEquals(createdStock.getCompanyName(), data.companyName());
    verify(stockRepository, times(1)).save(any());
  }

  @Test
  void updateStock_ValidData_ReturnsUpdatedStock() {
    String id = "1";
    Stock existingStock = new Stock("1", "Test4", "Test s.a", 150.00);
    Stock updatedStock = new Stock("1", "UPTD4", "Update Company s.a", 160.00);
    when(stockRepository.findById(id)).thenReturn(Optional.of(existingStock));
    when(stockRepository.save(any())).thenAnswer((Answer<Stock>) invocation -> invocation.getArgument(0));

    Optional<Stock> updatedOptionalStock = stockService.updateStock(id, updatedStock);

    assertTrue(updatedOptionalStock.isPresent());
    Stock returnedStock = updatedOptionalStock.get();
    assertEquals(updatedStock.getSymbol(), returnedStock.getSymbol());
    assertEquals(updatedStock.getCompanyName(), returnedStock.getCompanyName());
    assertEquals(updatedStock.getPrice(), returnedStock.getPrice());
    verify(stockRepository, times(1)).findById(id);
    verify(stockRepository, times(1)).save(any());
  }

  @Test
  void validateRequestStockDataTransferObject_ValidData_NoExceptionThrown() {
    RequestStockDataTransferObject data = new RequestStockDataTransferObject("ABCD1", "Company Name", 10.5);

    assertDoesNotThrow(() -> StockService.validateRequestStockDataTransferObject(data));
  }

  @Test
  void validateRequestStockDataTransferObject_InvalidData_ConstraintViolationExceptionThrown() {
    RequestStockDataTransferObject data = new RequestStockDataTransferObject("AB1", "", null);

    assertThrows(ConstraintViolationException.class, () -> StockService.validateRequestStockDataTransferObject(data));
  }
}
