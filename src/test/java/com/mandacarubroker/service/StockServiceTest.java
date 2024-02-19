package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for StockService.
 */
@ActiveProfiles("test")
@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    /**
     * Sets up test data before each test method.
     */
    @BeforeEach
    public void setUp() {
        stockRepository.save(new Stock(new RequestStockDTO("JPN5", "AMAZON 2", 76.24)));
        stockRepository.save(new Stock(new RequestStockDTO("NJK5", "GOOGLE", 205.6)));
        stockRepository.save(new Stock(new RequestStockDTO("KNU9", "APPLE", 670.5)));
    }

    /**
     * Cleans up test data after each test method.
     */
    @AfterEach
    public void tearDown() {
        stockRepository.deleteAll();
    }

    /**
     * Nested test class for stock retrieval.
     */
    @Nested
    @DisplayName("Stock Retrieval Tests")
    class StockRetrievalTests {
        /**
         * Test to retrieve all stocks.
         */
        @Test
        @DisplayName("Should Retrieve All Stocks")
        void shouldRetrieveAllStocks() {
            List<Stock> expectedStocks = List.of(
                    new Stock(new RequestStockDTO("JPN5", "AMAZON 2", 76.24)),
                    new Stock(new RequestStockDTO("NJK5", "GOOGLE", 205.6)),
                    new Stock(new RequestStockDTO("KNU9", "APPLE", 670.5))
            );

            List<Stock> retrievedStocks = stockService.getAllStocks();

            for (int i = 0; i < retrievedStocks.size(); i++) {
                assertEquals(retrievedStocks.get(i).getSymbol(), expectedStocks.get(i).getSymbol());
                assertEquals(retrievedStocks.get(i).getCompanyName(), expectedStocks.get(i).getCompanyName());
                assertEquals(retrievedStocks.get(i).getPrice(), expectedStocks.get(i).getPrice());
            }
        }

        /**
         * Test to retrieve stock by ID.
         */
        @Test
        @DisplayName("Should Retrieve Stock by ID")
        void shouldRetrieveStockById() {
            Stock targetStock = stockRepository.findAll().get(0);

            Optional<Stock> retrievedStock = stockService.getStockById(targetStock.getId());

            assertEquals(retrievedStock.get().getSymbol(), targetStock.getSymbol());
            assertEquals(retrievedStock.get().getCompanyName(), targetStock.getCompanyName());
            assertEquals(retrievedStock.get().getPrice(), targetStock.getPrice());
        }
    }
    /**
     * Nested test class for stock update and deletion.
     */
    @Nested
    @DisplayName("Stock Update and Deletion Tests")
    class StockUpdateAndDeletionTests {
        /**
         * Test to update stock.
         */
        @Test
        @DisplayName("Should Update Stock")
        void shouldUpdateStock() {
            Stock stockForUpdate = new Stock(new RequestStockDTO("ITU2", "Intel 3", 312.23));

            Stock targetUpdatingStock = stockRepository.findAll().get(0);

            stockService.updateStock(targetUpdatingStock.getId(), stockForUpdate);

            Optional<Stock> updatedStock = stockRepository.findById(targetUpdatingStock.getId());

            assertEquals(stockForUpdate.getSymbol(), updatedStock.get().getSymbol());
            assertEquals(stockForUpdate.getCompanyName(), updatedStock.get().getCompanyName());
            assertEquals(stockForUpdate.getPrice(), updatedStock.get().getPrice());
        }

        /**
         * Test to delete stock.
         */
        @Test
        @DisplayName("Should Delete Stock")
        void shouldDeleteStock() {
            Stock targetDeletingStock = stockRepository.findAll().get(0);

            stockService.deleteStock(targetDeletingStock.getId());

            assertEquals(Optional.empty(), stockRepository.findById(targetDeletingStock.getId()));
        }
    }
    /**
     * Nested test class for stock creation and validation.
     */
    @Nested
    @DisplayName("Stock Creation and Validation Tests")
    class StockCreationAndValidationTests {
        /**
         * Test to create a new stock.
         */
        @Test
        @DisplayName("Should Create New Stock")
        void shouldCreateNewStock() {
            RequestStockDTO newStockDTO = new RequestStockDTO("ITU2", "Intel 2", 256.75);

            stockService.createStock(newStockDTO);

            Stock retrievedStock = stockRepository.findAll().get(3);

            assertEquals(newStockDTO.symbol(), retrievedStock.getSymbol());
            assertEquals(newStockDTO.companyName(), retrievedStock.getCompanyName());
            assertEquals(newStockDTO.price(), retrievedStock.getPrice());
        }

        /**
         * Test to ensure invalid stock creation throws exception.
         */
        @Test
        @DisplayName("Should Not Create Invalid New Stock")
        void shouldNotCreateInvalidNewStock() {
            RequestStockDTO newStockDTO = new RequestStockDTO("ITUU2", "Intel 2", 256.75);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                stockService.createStock(newStockDTO);
            });
        }

        /**
         * Test to ensure exception is thrown on invalid symbol validation.
         */
        @Test
        @DisplayName("Should Throw Exception on Validate RequestStockDTO with Invalid Symbol")
        void shouldThrowExceptionOnValidateRequestStockDTOWithInvalidSymbol() {
            RequestStockDTO dataToValidate =
                    new RequestStockDTO("ITUU2", "Intel 2", 256.75);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                StockService.validateRequestStockDTO(dataToValidate);
            });
        }

        /**
         * Test to ensure exception is thrown on blank company name validation.
         */
        @Test
        @DisplayName("Should Throw Exception on Validate RequestStockDTO with Blank Company Name")
        void shouldThrowExceptionOnValidateRequestStockDTOWithCompanyNameBlank() {
            RequestStockDTO dataToValidate =
                    new RequestStockDTO("ITU2", "  ", 256.75);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                StockService.validateRequestStockDTO(dataToValidate);
            });
        }

    }

}
