package com.mandacarubroker.modules.stock.service;

import com.mandacarubroker.modules.stock.Stock;
import com.mandacarubroker.modules.stock.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReadStockServiceTest {
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ReadStockService readStockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Retorna todas as ações")
    @Test
    void getAllStocks_ReturnsListOfStocks() {
        // Arrange
        List<Stock> expectedStocks = Arrays.asList(new Stock(), new Stock());
        when(stockRepository.findAll()).thenReturn(expectedStocks);

        // Act
        List<Stock> actualStocks = readStockService.findAll();

        // Assert
        assertEquals(expectedStocks, actualStocks);
    }

    @DisplayName("Retorna uma ação por ID")
    @Test
    void getStockById_ReturnsStockById() {
        // Arrange
        String stockId = "validId";
        Stock expectedStock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(expectedStock));

        // Act
        Stock actualStock = readStockService.findById(stockId);

        // Assert
        assertEquals(expectedStock, actualStock);
    }



}
