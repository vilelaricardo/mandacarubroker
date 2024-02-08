package com.mandacarubroker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.swing.text.html.Option;
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
    void itShouldHandlePostInvalidStockSymbol() throws Exception {
        String stockJsonString = "{\"symbol\":\"MDDAAA2\",\"companyName\":\"Fake Company\",\"price\":0.01}";

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePostInvalidStockPrice() throws Exception {
        String stockJsonString = "{\"symbol\":\"MD2\",\"companyName\":\"Mandacaru Inc.\",\"price\":\"40A\"}";

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnOkStatusAfterSucessfulPost() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        stock.setCompanyName("New Company Name");
        String stockJsonString = objectMapper.writeValueAsString(stock);

        RequestBuilder requestBuilder = post("/stocks")
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isOk();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnStockDataAfterSucessfulPut() throws Exception {
        Stock stock = service.getAllStocks().get(0);

        String newCompanyName = "Mandacaru Inc.";
        String newSymbol = "MD2";
        Double newPrice = 100.00;

        stock.setCompanyName(newCompanyName);
        stock.setSymbol(newSymbol);
        stock.setPrice(newPrice);

        String stockJsonString = objectMapper.writeValueAsString(stock);

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        Stock updatedStock = objectMapper.readValue(content, Stock.class);

        assertEquals(updatedStock.getCompanyName(), stock.getCompanyName());
        assertEquals(updatedStock.getSymbol(), stock.getSymbol());
        assertEquals(updatedStock.getPrice(), stock.getPrice());
    }

    @Test
    void itShouldReturnNotFoundWhenPutStockDoesNotExists() throws Exception {
        RequestBuilder requestBuilder = put("/stocks/dummy-stock-id");
        ResultMatcher matchStatus = status().isNotFound();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldHandlePutInvalidStockSymbol() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        stock.setSymbol("MDD2");
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
        String stockJsonString = "{\"symbol\":\"MD2\",\"companyName\":\"Mandacaru Inc.\",\"price\":\"40A\"}";

        RequestBuilder requestBuilder = put("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isBadRequest();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldNotBeAbleToPutStockId() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        String originalId = stock.getId();
        stock.setId("novo-id");

        String stockJsonString = objectMapper.writeValueAsString(stock);

        RequestBuilder requestBuilder = post("/stocks/" + stock.getId())
                .contentType("application/json")
                .content(stockJsonString);
        ResultMatcher matchStatus = status().isMethodNotAllowed();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldBeAbleToDeleteStock() throws Exception {
        Stock stock = service.getAllStocks().get(0);
        String stockId = stock.getId();

        RequestBuilder requestBuilder = delete("/stocks/" + stockId);
        ResultMatcher matchStatus = status().isOk();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
        assertEquals(Optional.empty(), service.getStockById(stockId));
    }

    @Test
    void itShouldHandleDeleteInvalidStock() throws Exception {
        RequestBuilder requestBuilder = delete("/stocks/dummy-stock-id");
        ResultMatcher matchStatus = status().isNotFound();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }
}
