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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
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
        RequestBuilder requestBuilder = get("/stocks/dummy-stock-id");
        ResultMatcher matchStatus = status().isNotFound();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnCreatedStatusAfterSucessfulPost() throws Exception {
        RequestStockDTO stockDTO = new RequestStockDTO("MDDC2", "Mandacaru Inc.", 0.01);
        String stockJsonString = objectMapper.writeValueAsString(stockDTO);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isCreated();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnStockDataAfterSucessfulPost() throws Exception {
        String stockSymbol = "MDDC2";
        String stockCompanyName = "Mandacaru Inc.";
        Double stockPrice = 9.99;

        RequestStockDTO stockDTO = new RequestStockDTO(stockSymbol, stockCompanyName, stockPrice);
        String stockJsonString = objectMapper.writeValueAsString(stockDTO);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        Stock createdStock = objectMapper.readValue(content, Stock.class);

        assertEquals(stockSymbol, createdStock.getSymbol());
        assertEquals(stockCompanyName, createdStock.getCompanyName());
        assertEquals(stockPrice, createdStock.getPrice());
    }

    @Test
    void itShouldHandlePostInvalidStockSymbol() throws Exception {
        String stockJsonString = "{\"symbol\":\"MDDAAA2\",\"companyName\":\"Fake Company\",\"price\":0.01}";

        System.out.println(stockJsonString);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePostInvalidStockPrice() throws Exception {
        String stockJsonString = "{\"symbol\":\"MDDC2\",\"companyName\":\"Mandacaru Inc.\",\"price\":\"40A\"}";

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnStockDataAfterSucessfulPut() throws Exception {
        Stock stock = service.getAllStocks().get(0);

        String newCompanyName = "Mandacaru Inc.";
        String newSymbol = "MDDC2";
        double newPrice = 100.00;

        RequestStockDTO stockDTO = new RequestStockDTO(newSymbol, newCompanyName, newPrice);
        String stockJsonString = objectMapper.writeValueAsString(stockDTO);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        Stock updatedStock = objectMapper.readValue(content, Stock.class);

        assertEquals(newCompanyName, updatedStock.getCompanyName());
        assertEquals(newSymbol, updatedStock.getSymbol());
        assertEquals(newPrice, updatedStock.getPrice());
    }

    @Test
    void itShouldReturnOkStatusAfterSucessfulPut() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        RequestStockDTO stockDTO = new RequestStockDTO("MDDC2", "Mandacaru Inc.", 100.00);
        String stockJsonString = objectMapper.writeValueAsString(stockDTO);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isOk();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnNotFoundWhenPutStockDoesNotExists() throws Exception {
        String stockJsonString = "{\"symbol\":\"MDDC2\",\"companyName\":\"Mandacaru Inc.\",\"price\":100.00}";
        RequestBuilder requestBuilder = put("/stocks/dummy-stock-id")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isNotFound();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePutInvalidStockSymbol() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        stock.setSymbol("D2");
        String stockJsonString = objectMapper.writeValueAsString(stock);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePutInvalidStockPrice() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        String stockJsonString = "{\"symbol\":\"MDDC2\",\"companyName\":\"Mandacaru Inc.\",\"price\":\"40A\"}";

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);
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
        RequestBuilder requestBuilder = delete("/stocks/dummy-stock-id");
        ResultMatcher matchStatus = status().isNoContent();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }
}
