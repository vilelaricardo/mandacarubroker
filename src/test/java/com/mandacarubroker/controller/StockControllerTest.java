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
    @DisplayName("Testes de criação de ações")
    class StockCreationTests {
        // Este conjunto de testes verifica o comportamento da API ao criar novas ações.
        // Testa se uma nova ação pode ser criada com sucesso.
        // Testa se a API responde com o status 'Criado' ao criar uma nova ação.
        @Test
        @DisplayName("Deveria criar uma nova ação")
        void shouldCreateNewStock() throws Exception {
            RequestStockDTO newStockDTO = new RequestStockDTO("ITU2", "Itau 2", 256.75);

            String requestJson = objectMapper.writeValueAsString(newStockDTO);

            RequestBuilder postRequest = MockMvcRequestBuilders
                    .post("/stocks")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(postRequest)
                    .andExpect(jsonPath("$.symbol").value(newStockDTO.symbol()))
                    .andExpect(jsonPath("$.companyName").value(newStockDTO.companyName()))
                    .andExpect(jsonPath("$.price").value(newStockDTO.price()));
        }

        @Test
        @DisplayName("Deveria responder com status 'Criado' ao criar uma nova ação")
        void shouldRespondWithCreatedStatusWhenCreatingNewStock() throws Exception{
            RequestStockDTO newStockDTO = new RequestStockDTO("ITU2", "Itau 2", 256.75);

            String requestJson = objectMapper.writeValueAsString(newStockDTO);

            RequestBuilder postRequest = MockMvcRequestBuilders
                    .post("/stocks")
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(postRequest)
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    @DisplayName("Testes de atualização de ações")
    class StockUpdateTests {
        // Este conjunto de testes verifica o comportamento da API ao atualizar ações existentes.
        // Testa se uma ação existente pode ser atualizada com sucesso.
        // Testa se a API responde com o status 'OK' ao atualizar uma ação.
        // Testa se a API responde com o status 'Não encontrado' ao tentar atualizar uma ação com ID inexistente.
        @Test
        @DisplayName("Deveria atualizar uma ação")
        void shouldUpdateStock() throws Exception {
            Stock stockToUpdate = stockRepository.findAll().get(0);

            stockToUpdate.setSymbol("PTL3");

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
        @DisplayName("Deveria responder com status OK ao atualizar uma ação")
        void shouldRespondWithOkStatusWhenUpdatingStock() throws Exception{
            Stock stockToUpdate = stockRepository.findAll().get(0);

            stockToUpdate.setSymbol("PTL3");

            String requestJson = objectMapper.writeValueAsString(stockToUpdate);

            RequestBuilder putRequest = MockMvcRequestBuilders
                    .put("/stocks/{id}", stockToUpdate.getId())
                    .content(requestJson)
                    .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(putRequest)
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Deveria responder com status 'Não encontrado' ao atualizar uma ação com ID inexistente")
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
    @DisplayName("Testes de exclusão de ações")
    class StockDeletionTests {
        // Este conjunto de testes verifica o comportamento da API ao excluir ações existentes.
        // Testa se uma ação existente pode ser excluída com sucesso.
        // Testa se a API responde com o status 'Sem conteúdo' ao excluir uma ação.
        // Testa se a API responde com o status 'Sem conteúdo' ao tentar excluir uma ação com ID inexistente.
        @Test
        @DisplayName("Deveria excluir uma ação")
        void shouldDeleteStock() throws Exception  {
            Stock stockToDelete = stockRepository.findAll().get(0);

            RequestBuilder deleteRequest = MockMvcRequestBuilders
                    .delete("/stocks/{id}", stockToDelete.getId());

            mockMvc.perform(deleteRequest);
            assertEquals(2, stockRepository.count());
        }

        @Test
        @DisplayName("Deveria responder com status 'Sem conteúdo' ao excluir uma ação")
        void shouldRespondWithNoContentStatusWhenDeletingStock() throws Exception {
            Stock stockToDelete = stockRepository.findAll().get(0);

            RequestBuilder deleteRequest = MockMvcRequestBuilders
                    .delete("/stocks/{id}", stockToDelete.getId());

            mockMvc.perform(deleteRequest)
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Deveria responder com status 'Sem conteúdo' ao excluir uma ação com ID inexistente")
        void shouldRespondWithNoContentStatusWhenDeletingStockWithNonexistentId() throws Exception  {
            String nonexistentId = "g845nk7b56";

            RequestBuilder deleteRequest = MockMvcRequestBuilders
                    .delete("/stocks/{id}", nonexistentId);

            mockMvc.perform(deleteRequest)
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Testes de busca de ações")
    class StockRetrievalTests {
        // Este conjunto de testes verifica o comportamento da API ao buscar ações existentes.
        // Testa se todas as ações podem ser obtidas corretamente.
        // Testa se a API responde com o status 'OK' ao buscar todas as ações.
        // Testa se uma ação específica pode ser obtida corretamente pelo seu ID.
        // Testa se a API responde com o status 'Não encontrado' ao tentar obter uma ação inexistente pelo seu ID.
        @Test
        @DisplayName("Deveria obter todas as ações")
        void shouldFetchAllStocks() throws Exception {
            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks");

            mockMvc.perform(getRequest)
                    .andExpect(jsonPath("$.length()").value(3));
        }

        @Test
        @DisplayName("Deveria responder com status OK ao obter todas as ações")
        void shouldRespondWithOkStatusWhenFetchingAllStocks() throws Exception {
            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks");

            mockMvc.perform(getRequest)
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Deveria recuperar uma ação pelo ID")
        void shouldRetrieveStockById() throws Exception {
            Stock firstStock = stockRepository.findAll().get(0);

            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks/{id}", firstStock.getId());

            mockMvc.perform(getRequest)
                    .andExpect(jsonPath("$.symbol").value(firstStock.getSymbol()))
                    .andExpect(jsonPath("$.companyName").value(firstStock.getCompanyName()))
                    .andExpect(jsonPath("$.price").value(firstStock.getPrice()));
        }

        @Test
        @DisplayName("Deveria responder com status OK ao obter uma ação pelo ID")
        void shouldRespondWithOkStatusWhenFetchingStockById() throws Exception {
            Stock firstStock = stockRepository.findAll().get(0);

            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks/{id}", firstStock.getId());

            mockMvc.perform(getRequest)
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Deveria responder com status 'Não encontrado' ao obter uma ação inexistente pelo ID")
        void shouldRespondWithNotFoundStatusWhenFetchingNonexistentStockById() throws Exception {
            String nonexistentId = "a1b2b3bc4b5";
            RequestBuilder getRequest = MockMvcRequestBuilders.get("/stocks/{id}", nonexistentId);

            mockMvc.perform(getRequest)
                    .andExpect(status().isNotFound());
        }
    }
}
