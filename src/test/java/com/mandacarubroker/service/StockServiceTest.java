package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest



class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void initRepository() {
        stockRepository.save(new Stock(new RequestStockDTO("RPM3", "3R PETROLEUM", 90.45)));
        stockRepository.save(new Stock(new RequestStockDTO("ALL3", "ALLOS", 121.60)));
        stockRepository.save(new Stock(new RequestStockDTO("AZL4", "AZUL", 230.20)));
    }

    @AfterEach
    public void clearRepository() {
        stockRepository.deleteAll();
    }

    @Test
    void ShouldRetrieveAllStocks() {

        List<Stock> expectedStocks = List.of(
                new Stock(new RequestStockDTO("RPM3", "3R PETROLEUM", 90.45)),
                new Stock(new RequestStockDTO("ALL3", "ALLOS", 121.60)),
                new Stock(new RequestStockDTO("AZL4", "AZUL", 230.20))
        );

        List<Stock> retrievesStocks = stockService.getAllStocks();

        for (int i = 0; i < retrievesStocks.size(); i++) {
            assertEquals(retrievesStocks.get(i).getSymbol(), expectedStocks.get(i).getSymbol());
            assertEquals(retrievesStocks.get(i).getCompanyName(), expectedStocks.get(i).getCompanyName());
            assertEquals(retrievesStocks.get(i).getPrice(), expectedStocks.get(i).getPrice());
        }


    }

    @Test
    void ShouldGetStockById() {
        Stock targetStock = stockRepository.findAll().get(0);

        Optional<Stock> retrievesStocks = stockService.getStockById(targetStock.getId());

        assertEquals(retrievesStocks.get().getSymbol(), targetStock.getSymbol());
        assertEquals(retrievesStocks.get().getCompanyName(), targetStock.getCompanyName());
        assertEquals(retrievesStocks.get().getPrice(), targetStock.getPrice());

    }

    @Test
    void shouldAddNewStock() {

        RequestStockDTO newStock = new RequestStockDTO("CMG4", "CEMIG", 129.67);

        stockService.createStock(newStock);

        Stock retrievesStock = stockRepository.findAll().get(3);

        assertEquals(newStock.symbol(), retrievesStock.getSymbol());
        assertEquals(newStock.companyName(), retrievesStock.getCompanyName());
        assertEquals(newStock.price(), retrievesStock.getPrice());

    }


    @Test
    void shouldNotAddInvalidNewStock() {
        RequestStockDTO newStock = new RequestStockDTO("CMIG4", "CEMIG", 129.67);

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            stockService.createStock(newStock);
        });

    }

    @Test
    void shouldUpdateTheStock() {

        Stock stockForUpdate = new Stock(new RequestStockDTO("RPM2", "2R PETROLEUM", 103.95));

        Stock targetUpdatingStock = stockRepository.findAll().get(0);

        stockService.updateStock(targetUpdatingStock.getId(), stockForUpdate);

        Optional<Stock> updatedStock = stockRepository.findById(targetUpdatingStock.getId());

        assertEquals(stockForUpdate.getSymbol(), updatedStock.get().getSymbol());
        assertEquals(stockForUpdate.getCompanyName(), updatedStock.get().getCompanyName());
        assertEquals(stockForUpdate.getPrice(), updatedStock.get().getPrice());

    }

    @Test
    void shouldDeleteStock() {

        Stock targetDeletingStock = stockRepository.findAll().get(0);

        stockService.deleteStock(targetDeletingStock.getId());

        assertEquals(Optional.empty(), stockRepository.findById(targetDeletingStock.getId()));
    }

    @Test
    void shouldNotThrowsAnExceptionWhenValidatingRequestStockDTO(){

        RequestStockDTO dataToValidate = new RequestStockDTO("RPM2", "2R PETROLEUM", 103.95);

        assertDoesNotThrow(() -> {
            StockService.validateRequestStockDTO(dataToValidate);
        });
    }

    @Test
    void shouldThrowAnExceptionWhenValidatingRequestStockDTOWithAnInvalidSymbol() {

        RequestStockDTO dataToValidate =
                new RequestStockDTO("RPTM2", "2R PETROLEUM", 103.95);

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            StockService.validateRequestStockDTO(dataToValidate);
        });
    }

    @Test
    void shouldThrowAnExceptionWhenValidatingRequestStockDTOWithABlankCompanyName() {

        RequestStockDTO dataToValidate =
                new RequestStockDTO("RPM2", "   ", 103.95);

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            StockService.validateRequestStockDTO(dataToValidate);
        });
    }

    @Test
    void shouldThrowAnExceptionWhenValidatingTheRequestStockDTOWithNullPrice() {

        RequestStockDTO dataToValidate =
                new RequestStockDTO("RPM2", "2R PETROLEUM", 0);

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            StockService.validateRequestStockDTO(dataToValidate);
        });
    }

    @Test
    void shouldThrowsAnExceptionWhenValidatinRequestStockDTOWithNegativePrice() {
        RequestStockDTO dataToValidate =
                new RequestStockDTO("RPM2", "2R PETROLEUM", -103.95);

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            StockService.validateRequestStockDTO(dataToValidate);
        });
    }
}