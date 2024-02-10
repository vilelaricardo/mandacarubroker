package com.mandacarubroker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
class StockServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StockService service;

    private double stockSize;
    private final String validSymbol = "AAPL1";
    private final String emptySymbol = "";
    private final String invalidSymbol = "AAPL@";
    private final String validCompanyName = "Apple Inc.";
    private final String emptyCompanyName = "";
    private final double validPrice = 150.00;
    private final double negativePrice = -1.00;
    private final double zeroPrice = 0.00;

    @BeforeEach
    void setUp() {
        stockSize = service.getAllStocks().size();
    }

    @AfterEach
    void tearDown() {
    }

    void assertNoStockWasCreated() {
        assertEquals(stockSize, service.getAllStocks().size());
    }

    @Test
    void itShouldBeAbleToGetAllStocks() {
        List<Stock> stocks = service.getAllStocks();
        assertEquals(stockSize, stocks.size());
    }

    @Test
    void itShouldBeAbleToGetStockById() {
        List<Stock> stocks = service.getAllStocks();
        Stock stock = stocks.get(0);
        Optional<Stock> foundStock = service.getStockById(stock.getId());

        assertEquals(stock.getId(), foundStock.get().getId());
        assertEquals(stock.getSymbol(), foundStock.get().getSymbol());
        assertEquals(stock.getCompanyName(), foundStock.get().getCompanyName());
        assertEquals(stock.getPrice(), foundStock.get().getPrice());
    }

    @Test
    void itShouldRetunEmptyWhenStockIsNotFound() {
        Optional<Stock> foundStock = service.getStockById("dummy-stock-id");
        assertEquals(Optional.empty(), foundStock);
    }

    @Test
    void itShouldBeAbleToCreateStock() {
        RequestStockDTO newStockDTO = new RequestStockDTO(validSymbol, validCompanyName, validPrice);
        Stock createdStock = service.createStock(newStockDTO);

        assertEquals(stockSize + 1, service.getAllStocks().size());
        assertEquals(validSymbol, createdStock.getSymbol());
        assertEquals(validCompanyName, createdStock.getCompanyName());
        assertEquals(validPrice, createdStock.getPrice());
    }

    @Test
    void itShouldNotBeAbleToCreateStockWithEmptySymbol() {
        RequestStockDTO newStockDTO = new RequestStockDTO(emptySymbol, validCompanyName, validPrice);

        assertThrows(ConstraintViolationException.class, () -> {
            service.createStock(newStockDTO);
        });

        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToCreateStockWithInvalidSymbol() {
        RequestStockDTO newStockDTO = new RequestStockDTO(invalidSymbol, validCompanyName, validPrice);

        assertThrows(ConstraintViolationException.class, () -> {
            service.createStock(newStockDTO);
        });

        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToCreateStockWithEmptyCompanyName() {
        RequestStockDTO newStockDTO = new RequestStockDTO(validSymbol, emptyCompanyName, validPrice);

        assertThrows(ConstraintViolationException.class, () -> {
            service.createStock(newStockDTO);
        });

        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToCreateStockWithNegativePrice() {
        RequestStockDTO newStockDTO = new RequestStockDTO(validSymbol, validCompanyName, negativePrice);

        assertThrows(IllegalArgumentException.class, () -> {
            service.createStock(newStockDTO);
        });

        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToCreateStockWithZeroPrice() {
        RequestStockDTO newStockDTO = new RequestStockDTO(validSymbol, validCompanyName, zeroPrice);

        assertThrows(IllegalArgumentException.class, () -> {
            service.createStock(newStockDTO);
        });

        assertNoStockWasCreated();
    }

    @Test
    void itShouldBeAbleToUpdateStock() {
        Stock stock = service.getAllStocks().get(0);

        RequestStockDTO stockDTO = new RequestStockDTO(validSymbol, validCompanyName, validPrice);
        Optional<Stock> updatedStock = service.updateStock(stock.getId(), stockDTO);

        assertEquals(validCompanyName, updatedStock.get().getCompanyName());
        assertEquals(validSymbol, updatedStock.get().getSymbol());
        assertEquals(validPrice, updatedStock.get().getPrice());
        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToUpdateStockWithEmptySymbol() {
        Stock stock = service.getAllStocks().get(0);
        RequestStockDTO stockDTO = new RequestStockDTO(emptySymbol, validCompanyName, validPrice);
        assertThrows(ConstraintViolationException.class, () -> {
            service.updateStock(stock.getId(), stockDTO);
        });
        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToUpdateStockWithInvalidSymbol() {
        Stock stock = service.getAllStocks().get(0);
        RequestStockDTO stockDTO = new RequestStockDTO(invalidSymbol, validCompanyName, validPrice);
        assertThrows(ConstraintViolationException.class, () -> {
            service.updateStock(stock.getId(), stockDTO);
        });
        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToUpdateStockWithEmptyCompanyName() {
        Stock stock = service.getAllStocks().get(0);
        RequestStockDTO stockDTO = new RequestStockDTO(validSymbol, emptyCompanyName, validPrice);
        assertThrows(ConstraintViolationException.class, () -> {
            service.updateStock(stock.getId(), stockDTO);
        });
        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToUpdateStockWithNegativePrice() {
        Stock stock = service.getAllStocks().get(0);
        RequestStockDTO stockDTO = new RequestStockDTO(validSymbol, validCompanyName, negativePrice);
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateStock(stock.getId(), stockDTO);
        });
        assertNoStockWasCreated();
    }

    @Test
    void itShouldNotBeAbleToUpdateStockWithZeroPrice() {
        Stock stock = service.getAllStocks().get(0);
        RequestStockDTO stockDTO = new RequestStockDTO(validSymbol, validCompanyName, zeroPrice);
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateStock(stock.getId(), stockDTO);
        });
        assertNoStockWasCreated();
    }

    @Test
    void itShouldBeAbleToDeleteStock() {
        Stock stock = service.getAllStocks().get(0);
        service.deleteStock(stock.getId());

        Optional<Stock> deletedStock = service.getStockById(stock.getId());
        assertEquals(Optional.empty(), deletedStock);
        assertEquals(stockSize - 1, service.getAllStocks().size());
    }

    @Test
    void itShouldNotBeAbleToDeleteStockThatDoesNotExists() {
        service.deleteStock("dummy-stock-id");
        assertNoStockWasCreated();
    }
}