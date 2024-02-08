package com.mandacarubroker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void itShouldReturnNotFoundWhenStockDoesNotExists() throws Exception {
        RequestBuilder requestBuilder = get("/stocks/dummy-stock-id");
        ResultMatcher matchStatus = status().isNotFound();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnCreatedStatusAfterSucessfulPost() throws Exception {
        RequestStockDTO stockDTO = new RequestStockDTO("MD2", "Mandacaru Inc.", 0.01);
        String stockJsonString = objectMapper.writeValueAsString(stockDTO);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isCreated();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnStockDataAfterSucessfulPost() throws Exception {
        String stockSymbol = "MD2";
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

        assertEquals(createdStock.getSymbol(), stockSymbol);
        assertEquals(createdStock.getCompanyName(), stockCompanyName);
        assertEquals(createdStock.getPrice(), stockPrice);
    }

    @Test
    void itShouldHandleInvalidStockSymbol() throws Exception {
        RequestStockDTO stockDTO = new RequestStockDTO("MDD2", "Fake Company", 0.01);
        String stockJsonString = objectMapper.writeValueAsString(stockDTO);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandleInvalidStockPrice() throws Exception {
        String stockJsonString = "{\"symbol\":\"MD2\",\"companyName\":\"Mandacaru Inc.\",\"price\":\"40A\"}";

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }
}
