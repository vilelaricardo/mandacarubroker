package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.MandacaruBrokerApplication;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MandacaruBrokerApplication.class)
@AutoConfigureMockMvc
@Transactional
class StockControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StockRepository stockRepository;
    private Stock stock;
    private Long totalStocks;
    private String existingId;

    @BeforeEach
    void setup(){
        stock= new Stock("1","ABC1","ABC COMPANY",50.0);
        Stock savedStock = stockRepository.save(stock);
        totalStocks=stockRepository.count();
        existingId = savedStock.getId();
    }
    @Test
    void createShouldCreateAStock() throws Exception {
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(),stock.getCompanyName(),stock.getPrice());
        String jsonObject = objectMapper.writeValueAsString(validDto);

        MvcResult result = mockMvc.perform(post("/stocks").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
                .andExpect(status().isCreated()).andReturn();

        String jsonRespStock = result.getResponse().getContentAsString();
        Stock respStock= objectMapper.readValue(jsonRespStock,Stock.class);

        assertEquals(totalStocks+1,stockRepository.count());
        assertTrue(stockRepository.existsById(respStock.getId()));
    }

    @Test
    void createShouldNotCreateAStockWhenInvalidInput() throws Exception {
        RequestStockDTO invalidDto = new RequestStockDTO("AB","",0);
        String jsonObject = objectMapper.writeValueAsString(invalidDto);

        mockMvc.perform(post("/stocks").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Validation failed")));

        assertEquals(totalStocks,stockRepository.count());
    }

    @Test
    void findByIdShouldReturnAStock() throws Exception {

        mockMvc.perform(get("/stocks/{id}",existingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.notNullValue()))
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.symbol").value(stock.getSymbol()))
                .andExpect(jsonPath("$.companyName").value(stock.getCompanyName()))
                .andExpect(jsonPath("$.price").value(stock.getPrice()));

    }
    @Test
    void updateShouldUpdateAStock() throws Exception {
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(),stock.getCompanyName(),stock.getPrice());
        double expectedPrice = validDto.price()+stock.getPrice();
        String jsonObject = objectMapper.writeValueAsString(validDto);

        mockMvc.perform(put("/stocks/{id}",existingId).contentType(MediaType.APPLICATION_JSON).content(jsonObject))
                .andExpect(status().isOk()).andExpect(content().string(notNullValue()));

        Stock updatedStock = stockRepository.findById(existingId).orElseThrow();

        assertEquals(totalStocks,stockRepository.count());
        assertEquals(validDto.symbol(),updatedStock.getSymbol());
        assertEquals(validDto.companyName(),updatedStock.getCompanyName());
        assertEquals(expectedPrice,updatedStock.getPrice());
    }

    @Test
    void updateShouldNotUpdateAStockWhenInvalidData() throws Exception {
        RequestStockDTO invalidDto = new RequestStockDTO("","",0);
        String jsonObject = objectMapper.writeValueAsString(invalidDto);
        Stock beforeUpdate = stockRepository.findById(existingId).orElseThrow();

        mockMvc.perform(put("/stocks/{id}",existingId).contentType(MediaType.APPLICATION_JSON).content(jsonObject))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Validation failed")));

        Stock updatedStock = stockRepository.findById(existingId).orElseThrow();

        assertEquals(totalStocks,stockRepository.count());
        assertEquals(beforeUpdate.getSymbol(),updatedStock.getSymbol());
        assertEquals(beforeUpdate.getCompanyName(),updatedStock.getCompanyName());
        assertEquals(beforeUpdate.getPrice(),updatedStock.getPrice());
    }

    @Test
    void deleteShouldDeleteAStock() throws Exception {
        mockMvc.perform(delete("/stocks/{id}",existingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(blankOrNullString()));

        assertEquals(totalStocks-1,stockRepository.count());
        assertTrue(stockRepository.findById(existingId).isEmpty());
    }

    @Test
    void deleteShouldNotDeleteAStock() throws Exception {
        String nonExistingId = "none";
        mockMvc.perform(delete("/stocks/{id}",nonExistingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Stock Not Found")));

        assertEquals(totalStocks,stockRepository.count());
        assertTrue(stockRepository.findById(nonExistingId).isEmpty());
    }
}
