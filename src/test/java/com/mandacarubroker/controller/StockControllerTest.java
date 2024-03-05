package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.controller.exceptions.StandardError;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.dtos.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.service.StockService;
import com.mandacarubroker.service.TokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(controllers = StockController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
class StockControllerTest {

    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StockService stockService;
    @Autowired
    private ObjectMapper objectMapper;
    private Stock stock;
    private String existingId,nonExistingId;
    private StandardError errorNotFound;
    private final String NOT_FOUND_MSG = "Stock Not Found";

    @BeforeEach
    void setup(){
        stock = new Stock("1","ABC1","TESTE",50.0);
        existingId = "1";
        nonExistingId = "none";

        errorNotFound = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(),"Entity Not Found",NOT_FOUND_MSG,
                UriComponentsBuilder.fromPath("/stocks/{id}").buildAndExpand(nonExistingId).toUriString());
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
        when(stockService.getStockById(nonExistingId)).thenThrow(new EntityNotFoundException("Stock Not Found"));
        mockMvc.perform(get("/stocks/{id}",nonExistingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instant").exists())
                .andExpect(jsonPath("$.status").value(errorNotFound.status()))
                .andExpect(jsonPath("$.error").value(errorNotFound.error()))
                .andExpect(jsonPath("$.message").value(errorNotFound.message()))
                .andExpect(jsonPath("$.path").value(errorNotFound.path()));
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
        when(stockService.updateStock(nonExistingId,validDto)).thenThrow(new EntityNotFoundException(NOT_FOUND_MSG));

        mockMvc.perform(put("/stocks/{id}",nonExistingId).content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instant").exists())
                .andExpect(jsonPath("$.status").value(errorNotFound.status()))
                .andExpect(jsonPath("$.error").value(errorNotFound.error()))
                .andExpect(jsonPath("$.message").value(errorNotFound.message()))
                .andExpect(jsonPath("$.path").value(errorNotFound.path()));
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
        doThrow(new EntityNotFoundException(NOT_FOUND_MSG)).when(stockService).deleteStock(nonExistingId);
        mockMvc.perform(delete("/stocks/{id}",nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instant").exists())
                .andExpect(jsonPath("$.status").value(errorNotFound.status()))
                .andExpect(jsonPath("$.error").value(errorNotFound.error()))
                .andExpect(jsonPath("$.message").value(errorNotFound.message()))
                .andExpect(jsonPath("$.path").value(errorNotFound.path()));
    }
}
