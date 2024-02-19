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

@ActiveProfiles("test")
@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        stockRepository.save(new Stock(new RequestStockDTO("JPN5", "NUBANK 2", 76.24)));
        stockRepository.save(new Stock(new RequestStockDTO("NJK5", "AURELO", 205.6)));
        stockRepository.save(new Stock(new RequestStockDTO("KNU9", "BANCO DO BRAZIL", 670.5)));
    }

    @AfterEach
    public void tearDown() {
        stockRepository.deleteAll();
    }

    @Nested
    @DisplayName("Testes de obtenção de ações")
    class StockRetrievalTests {
        @Test
        @DisplayName("Deve obter todas as ações")
        void shouldRetrieveAllStocks() {
            List<Stock> expectedStocks = List.of(
                    new Stock(new RequestStockDTO("JPN5", "NUBANK 2", 76.24)),
                    new Stock(new RequestStockDTO("NJK5", "AURELO", 205.6)),
                    new Stock(new RequestStockDTO("KNU9", "BANCO DO BRAZIL", 670.5))
            );

            List<Stock> retrievedStocks = stockService.getAllStocks();

            for (int i = 0; i < retrievedStocks.size(); i++) {
                assertEquals(retrievedStocks.get(i).getSymbol(), expectedStocks.get(i).getSymbol());
                assertEquals(retrievedStocks.get(i).getCompanyName(), expectedStocks.get(i).getCompanyName());
                assertEquals(retrievedStocks.get(i).getPrice(), expectedStocks.get(i).getPrice());
            }
        }

        @Test
        @DisplayName("Deve obter ação por ID")
        void shouldRetrieveStockById() {
            Stock targetStock = stockRepository.findAll().get(0);

            Optional<Stock> retrievedStock = stockService.getStockById(targetStock.getId());

            assertEquals(retrievedStock.get().getSymbol(), targetStock.getSymbol());
            assertEquals(retrievedStock.get().getCompanyName(), targetStock.getCompanyName());
            assertEquals(retrievedStock.get().getPrice(), targetStock.getPrice());
        }
    }

    @Nested
    @DisplayName("Testes de criação e validação de ações")
    class StockCreationAndValidationTests {
        @Test
        @DisplayName("Deve criar nova ação")
        void shouldCreateNewStock() {
            RequestStockDTO newStockDTO = new RequestStockDTO("ITU2", "Itau 2", 256.75);

            stockService.createStock(newStockDTO);

            Stock retrievedStock = stockRepository.findAll().get(3);

            assertEquals(newStockDTO.symbol(), retrievedStock.getSymbol());
            assertEquals(newStockDTO.companyName(), retrievedStock.getCompanyName());
            assertEquals(newStockDTO.price(), retrievedStock.getPrice());
        }

        @Test
        @DisplayName("Não deve criar nova ação inválida")
        void shouldNotCreateInvalidNewStock() {
            RequestStockDTO newStockDTO = new RequestStockDTO("ITUU2", "Itau 2", 256.75);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                stockService.createStock(newStockDTO);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao validar dados da ação com símbolo inválido")
        void shouldThrowExceptionOnValidateRequestStockDTOWithInvalidSymbol() {
            RequestStockDTO dataToValidate =
                    new RequestStockDTO("ITUU2", "Itau 2", 256.75);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                StockService.validateRequestStockDTO(dataToValidate);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao validar dados da ação com nome da empresa em branco")
        void shouldThrowExceptionOnValidateRequestStockDTOWithCompanyNameBlank() {
            RequestStockDTO dataToValidate =
                    new RequestStockDTO("ITU2", "  ", 256.75);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                StockService.validateRequestStockDTO(dataToValidate);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao validar dados da ação com preço nulo")
        void shouldThrowExceptionOnValidateRequestStockDTOWithPriceNull() {
            RequestStockDTO dataToValidate =
                    new RequestStockDTO("ITU2", "Itau 2", 0);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                StockService.validateRequestStockDTO(dataToValidate);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao validar dados da ação com preço negativo")
        void shouldThrowExceptionOnValidateRequestStockDTOWithPriceNegative() {
            RequestStockDTO dataToValidate =
                    new RequestStockDTO("ITU2", "Itau 2", -256.75);

            assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
                StockService.validateRequestStockDTO(dataToValidate);
            });
        }
    }

    @Nested
    @DisplayName("Testes de atualização e exclusão de ações")
    class StockUpdateAndDeletionTests {
        @Test
        @DisplayName("Deve atualizar ação")
        void shouldUpdateStock() {
            Stock stockForUpdate = new Stock(new RequestStockDTO("ITU2", "Itau 3", 312.23));

            Stock targetUpdatingStock = stockRepository.findAll().get(0);

            stockService.updateStock(targetUpdatingStock.getId(), stockForUpdate);

            Optional<Stock> updatedStock = stockRepository.findById(targetUpdatingStock.getId());

            assertEquals(stockForUpdate.getSymbol(), updatedStock.get().getSymbol());
            assertEquals(stockForUpdate.getCompanyName(), updatedStock.get().getCompanyName());
            assertEquals(stockForUpdate.getPrice(), updatedStock.get().getPrice());
        }

        @Test
        @DisplayName("Deve excluir ação")
        void shouldDeleteStock() {
            Stock targetDeletingStock = stockRepository.findAll().get(0);

            stockService.deleteStock(targetDeletingStock.getId());

            assertEquals(Optional.empty(), stockRepository.findById(targetDeletingStock.getId()));
        }
    }
}
