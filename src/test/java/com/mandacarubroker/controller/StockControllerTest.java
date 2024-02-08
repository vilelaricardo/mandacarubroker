package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
