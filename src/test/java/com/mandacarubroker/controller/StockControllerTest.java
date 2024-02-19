package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setupTestEnvironment() {
        stockRepository.save(new Stock(new RequestStockDTO("ABC1", "XYZ 2", 76.24)));
        stockRepository.save(new Stock(new RequestStockDTO("DEF2", "GHIJK", 205.6)));
        stockRepository.save(new Stock(new RequestStockDTO("LMN3", "OPQ 4", 670.5)));
    }

    @AfterEach
    public void cleanupDatabase() {
        stockRepository.deleteAll();
    }

    @Nested
    @DisplayName("Stock Creation Tests")
    class StockCreationTests {

        @Test
        @DisplayName("Should create a new stock")
        void shouldCreateNewStock() throws Exception {
            RequestStockDTO newStockRequestDTO = new RequestStockDTO("RST4", "UVW 5", 256.75);

            String requestJson = objectMapper.writeValueAsString(newStockRequestDTO);

            RequestBuilder postRequest = MockMvcRequestBuilders
                    .post("/stocks")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(postRequest)
                    .andExpect(jsonPath("$.symbol").value(newStockRequestDTO.symbol()))
                    .andExpect(jsonPath("$.companyName").value(newStockRequestDTO.companyName()))
                    .andExpect(jsonPath("$.price").value(newStockRequestDTO.price()));
        }

        @Test
        @DisplayName("Should respond with created status when creating new stock")
        void shouldRespondWithCreatedStatusWhenCreatingNewStock() throws Exception{
            RequestStockDTO newStockRequestDTO = new RequestStockDTO("RST4", "UVW 5", 256.75);

            String requestJson = objectMapper.writeValueAsString(newStockRequestDTO);

            RequestBuilder postRequest = MockMvcRequestBuilders
                    .post("/stocks")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(postRequest)
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    @DisplayName("Stock Update Tests")
    class StockUpdateTests {

        @Test
        @DisplayName("Should update a stock")
        void shouldUpdateStock() throws Exception {
            Stock stockToUpdate = stockRepository.findAll().get(0);

            stockToUpdate.setSymbol("GHI1");

            String requestJson = objectMapper.writeValueAsString(stockToUpdate);

            RequestBuilder putRequest = MockMvcRequestBuilders
                    .put("/stocks/{id}", stockToUpdate.getId())
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(putRequest)
                    .andExpect(jsonPath("$.symbol").value(stockToUpdate.getSymbol()))
                    .andExpect(jsonPath("$.companyName").value(stockToUpdate.getCompanyName()))
                    .andExpect(jsonPath("$.price").value(stockToUpdate.getPrice()));
        }

        @Test
        @DisplayName("Should respond with OK status when updating a stock")
        void shouldRespondWithOkStatusWhenUpdatingStock() throws Exception{
            Stock stockToUpdate = stockRepository.findAll().get(0);

            stockToUpdate.setSymbol("GHI1");

            String requestJson = objectMapper.writeValueAsString(stockToUpdate);

            RequestBuilder putRequest = MockMvcRequestBuilders
                    .put("/stocks/{id}", stockToUpdate.getId())
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(putRequest)
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should respond with 'Not Found' status when updating a stock with nonexistent ID")
        void shouldRespondWithNotFoundStatusWhenUpdatingStockWithNonexistentId() throws Exception {
            String nonexistentId = "89c76g34d";
            String requestJson = "{"
                    + "\"symbol\":\"EQT3\","
                    + "\"companyName\":\"EQUATORIAL\","
                    + "\"price\":128.12"
                    + "}";

            RequestBuilder putRequest = MockMvcRequestBuilders
                    .put("/stocks/{id}", nonexistentId)
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(putRequest)
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Stock Deletion Tests")
    class StockDeletionTests {

        @Test
        @DisplayName("Should delete a stock")
        void shouldDeleteStock() throws Exception  {
            Stock stockToDelete = stockRepository.findAll().get(0);

            RequestBuilder deleteRequest = MockMvcRequestBuilders
                    .delete("/stocks/{id}", stockToDelete.getId());

            mockMvc.perform(deleteRequest);
            assertEquals(2, stockRepository.count());
        }

        @Test
        @DisplayName("Should respond with 'No Content' status when deleting a stock with nonexistent ID")
        void shouldRespondWithNoContentStatusWhenDeletingStockWithNonexistentId() throws Exception  {
            String nonexistentId = "a1b2c3d44";

            RequestBuilder deleteRequest = MockMvcRequestBuilders
                    .delete("/stocks/{id}", nonexistentId);

            mockMvc.perform(deleteRequest)
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("Stock Retrieval Tests")
    class StockRetrievalTests {

        @Test
        @DisplayName("Should fetch all stocks")
        void shouldFetchAllStocks() throws Exception {
            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks");

            mockMvc.perform(getRequest)
                    .andExpect(jsonPath("$.length()").value(3));
        }

        @Test
        @DisplayName("Should respond with OK status when fetching all stocks")
        void shouldRespondWithOkStatusWhenFetchingAllStocks() throws Exception {
            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks");

            mockMvc.perform(getRequest)
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should retrieve a stock by ID")
        void shouldRetrieveStockById() throws Exception {
            Stock firstStock = stockRepository.findAll().get(0);

            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks/{id}", firstStock.getId());

            mockMvc.perform(getRequest)
                    .andExpect(jsonPath("$.symbol").value(firstStock.getSymbol()))
                    .andExpect(jsonPath("$.companyName").value(firstStock.getCompanyName()))
                    .andExpect(jsonPath("$.price").value(firstStock.getPrice()));
        }

        @Test
        @DisplayName("Should respond with OK status when fetching a stock by ID")
        void shouldRespondWithOkStatusWhenFetchingStockById() throws Exception {
            Stock firstStock = stockRepository.findAll().get(0);

            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks/{id}", firstStock.getId());

            mockMvc.perform(getRequest)
                    .andExpect(status().isOk());
        }

    }
}
