package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    void getAllStockRequestStatusOk() throws Exception {
        this.mockMvc.perform(get("/stocks")).andDo(print()).andExpect(status().isOk());
    }

    

    @Test
    void getStockById() throws Exception {
        String id = "4948d1c1-ecd5-4840-bd19-70d955ae2fda";
        this.mockMvc.perform(get("/stocks/4948d1c1-ecd5-4840-bd19-70d955ae2fda")).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void getStockByIdMostReturnEmptyIfIdNotFound() throws Exception {
        this.mockMvc.perform(get("/stocks/notId")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void createNewStock() throws Exception {
        RequestStockDTO request = new RequestStockDTO("CP1", "Testing Company", 123);
        Stock stock = new Stock(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStock = objectMapper.writeValueAsString(stock);

        this.mockMvc.perform(post("/stocks").contentType("application/json").content(jsonStock))
                .andExpect(status().isOk());
    }

    @Test
    void updateStock() throws Exception {
        String id = "4948d1c1-ecd5-4840-bd19-70d955ae2fda";
        Random r = new Random();
        RequestStockDTO request =
                new RequestStockDTO("AB1", "Testing Company", r.nextDouble() * 100);
        Stock stock = new Stock(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStock = objectMapper.writeValueAsString(stock);

        this.mockMvc.perform(put("/stocks/" + id).contentType("application/json").content(jsonStock))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStock() throws Exception {
        String id = "4948d1c1-ecd5-4840-bd19-70d955ae2fda";
        this.mockMvc.perform(delete("/stocks/" + id)).andExpect(status().isOk());
    }
}
