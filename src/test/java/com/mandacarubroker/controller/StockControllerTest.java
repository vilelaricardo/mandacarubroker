package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
class StockControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StockService stockService;
    @Autowired
    private ObjectMapper objectMapper;
    private Stock stock;
    private String existingId,nonExistingId;

    @BeforeEach
    void setup(){
        stock = new Stock("1","ABC1","TESTE",50.0);
        existingId = "1";
        nonExistingId = "none";

    }

    @Test
    void getAllStocksShouldReturnStatusOK() throws Exception {
        when(stockService.getAllStocks()).thenReturn(List.of(stock));
        mockMvc.perform(get("/stocks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    void findByIdShouldReturnStatusOK() throws Exception {
        when(stockService.getStockById(existingId)).thenReturn(stock);
        mockMvc.perform(get("/stocks/{id}",existingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.symbol").value("ABC1"));
    }

    @Test
    void findByIdShouldReturnNotFoundStatus() throws Exception {
        when(stockService.getStockById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/stocks/{id}",nonExistingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnStatusCreated() throws Exception {
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(),stock.getCompanyName(),stock.getPrice());
        String jsonObject = objectMapper.writeValueAsString(validDto);
        when(stockService.createStock(validDto)).thenReturn(stock);

        mockMvc.perform(post("/stocks").content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(header().stringValues("location","/stocks/1"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.symbol").value("ABC1"));
    }

    @Test
    void createShouldReturnUnprocessableEntityStatus() throws Exception {
        RequestStockDTO invalidDto = new RequestStockDTO("","",0);
        String jsonObject = objectMapper.writeValueAsString(invalidDto);
        when(stockService.createStock(ArgumentMatchers.any())).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(post("/stocks").content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateShouldReturnOKStatus() throws Exception {
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(),stock.getCompanyName(),stock.getPrice());
        String jsonObject = objectMapper.writeValueAsString(validDto);
        when(stockService.updateStock(existingId,validDto)).thenReturn(stock);

        mockMvc.perform(put("/stocks/{id}",existingId).content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stock.getId()))
                .andExpect(jsonPath("$.symbol").value(stock.getSymbol()));
    }

    @Test
    void updateShouldReturnUnprocessableEntityStatus() throws Exception {
        RequestStockDTO invalidDto = new RequestStockDTO("","",0);
        String jsonObject = objectMapper.writeValueAsString(invalidDto);
        when(stockService.updateStock(anyString(),eq(invalidDto))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(put("/stocks/{id}",existingId).content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateShouldReturnNotFoundStatus() throws Exception {
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(),stock.getCompanyName(),stock.getPrice());
        String jsonObject = objectMapper.writeValueAsString(validDto);
        when(stockService.updateStock(nonExistingId,validDto)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/stocks/{id}",nonExistingId).content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(stockService).deleteStock(existingId);
        mockMvc.perform(delete("/stocks/{id}",existingId))
                .andExpect(status().isNoContent())
                .andExpect(content().string(blankOrNullString()));
    }

    @Test
    void deleteShouldReturnNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(stockService).deleteStock(nonExistingId);
        mockMvc.perform(delete("/stocks/{id}",nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(blankOrNullString()));
    }
}
