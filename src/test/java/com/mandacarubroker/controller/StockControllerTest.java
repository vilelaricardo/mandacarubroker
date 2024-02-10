package com.mandacarubroker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StockService service;

    private final String urlRequestInvalidStock = "/stocks/dummy-stock-id";
    private final String validSymbol = "MDDC2";
    private final String validCompanyName = "Mandacaru Inc.";
    private final double validPrice = 0.01;
    private final RequestStockDTO validStockDTO = new RequestStockDTO(validSymbol, validCompanyName, validPrice);
    private final RequestStockDTO invalidSymbolStockDTO = new RequestStockDTO("MDDAAA2", validCompanyName, validPrice);
    private final String invalidPriceStockJsonString = "{\"symbol\":\"MDDC2\",\"companyName\":\"Mandacaru Inc.\",\"price\":\"40A\"}";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    void assertRequestDTOEqualsStock(final RequestStockDTO stockDTO, final Stock stock) {
        assertEquals(stockDTO.symbol(), stock.getSymbol());
        assertEquals(stockDTO.companyName(), stock.getCompanyName());
        assertEquals(stockDTO.price(), stock.getPrice());
    }

    @Test
    void itShouldBeAbleToGetAllStocks() throws Exception {
        RequestBuilder requestBuilder = get("/stocks");
        ResultMatcher resultMatcher = status().isOk();
        List<Stock> allStocks = service.getAllStocks();
        mockMvc.perform(requestBuilder).andExpect(resultMatcher).equals(allStocks);
    }

    @Test
    void itShouldBeAbleToGetStockById() throws Exception {
        List<Stock> allStocks = service.getAllStocks();
        Stock stock = allStocks.get(0);
        String stockJsonString = objectMapper.writeValueAsString(stock);

        RequestBuilder requestBuilder = get("/stocks/" + stock.getId());
        ResultMatcher matchStatus = status().isOk();
        ResultMatcher matchResponse = content().json(stockJsonString);
        mockMvc.perform(requestBuilder).andExpectAll(matchStatus, matchResponse);
    }

    @Test
    void itShouldReturnNotFoundWhenGetStockDoesNotExists() throws Exception {
        RequestBuilder requestBuilder = get(urlRequestInvalidStock);
        ResultMatcher matchStatus = status().isNotFound();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnCreatedStatusAfterSucessfulPost() throws Exception {
        String stockJsonString = objectMapper.writeValueAsString(validStockDTO);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isCreated();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnStockDataAfterSucessfulPost() throws Exception {
        String stockJsonString = objectMapper.writeValueAsString(validStockDTO);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        Stock createdStock = objectMapper.readValue(content, Stock.class);

        assertRequestDTOEqualsStock(validStockDTO, createdStock);
    }

    @Test
    void itShouldHandlePostInvalidStockSymbol() throws Exception {
        String stockJsonString = objectMapper.writeValueAsString(invalidSymbolStockDTO);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePostInvalidStockPrice() throws Exception {
        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(invalidPriceStockJsonString);

        ResultMatcher matchStatus = status().isBadRequest();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnStockDataAfterSucessfulPut() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        String stockJsonString = objectMapper.writeValueAsString(validStockDTO);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        Stock updatedStock = objectMapper.readValue(content, Stock.class);

        assertRequestDTOEqualsStock(validStockDTO, updatedStock);
    }

    @Test
    void itShouldReturnOkStatusAfterSucessfulPut() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        String stockJsonString = objectMapper.writeValueAsString(validStockDTO);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isOk();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnNotFoundWhenPutStockDoesNotExists() throws Exception {
        String stockJsonString = objectMapper.writeValueAsString(validStockDTO);

        RequestBuilder requestBuilder = put(urlRequestInvalidStock)
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isNotFound();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePutInvalidStockSymbol() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        String stockJsonString = objectMapper.writeValueAsString(invalidSymbolStockDTO);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePutInvalidStockPrice() throws Exception {
        Stock stock = service.getAllStocks().get(0);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(invalidPriceStockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldNotBeAbleToPutStockId() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        final String actualStockId = stock.getId();

        stock.setId("novo-id");
        String stockJsonString = objectMapper.writeValueAsString(stock);

        RequestBuilder requestBuilder = put("/stocks/" + actualStockId)
                .contentType("application/json")
                .content(stockJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        Stock createdStock = objectMapper.readValue(content, Stock.class);

        assertEquals(actualStockId, createdStock.getId());
    }

    @Test
    void itShouldBeAbleToDeleteStock() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        String stockId = stock.getId();

        RequestBuilder requestBuilder = delete("/stocks/" + stockId);
        ResultMatcher matchStatus = status().isNoContent();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
        assertEquals(Optional.empty(), service.getStockById(stockId));
    }

    @Test
    void itShouldNotBeAbleToDeleteInvalidStock() throws Exception {
        RequestBuilder requestBuilder = delete(urlRequestInvalidStock);
        ResultMatcher matchStatus = status().isNoContent();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }
}
