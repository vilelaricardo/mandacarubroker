package com.mandacarubroker.integrationTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import com.mandacarubroker.domain.stock.Stock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static Stock savedStock;

  @AfterAll
  static void setUp() {
    savedStock = null;
  }

  @Test
  @Order(1)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void createStock() throws Exception {
    RequestStockDataTransferObject requestData = new RequestStockDataTransferObject("STOK1", "Test s.a", 10.0, 1);
    Stock stock = new Stock(requestData);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/stocks")
      .content("{\"symbol\":\"STOK1\", \"companyName\":\"Test s.a\", \"price\":10.0, \"amount\":1}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value(stock.getSymbol()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(stock.getCompanyName()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(stock.getPrice()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(stock.getAmount()))
      .andReturn();


    // Save the request result to use in future tests
    String responseBody = result.getResponse().getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<>() {});

    String id = (String) responseMap.get("id");
    String symbol = (String) responseMap.get("symbol");
    String companyName = (String) responseMap.get("companyName");
    Double price = (Double) responseMap.get("price");
    Integer amount = (Integer) responseMap.get("amount");

    savedStock = new Stock(id, symbol, companyName, price, amount);
  }

  @Test
  @Order(2)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void getAllStocks() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    List<Stock> stockList = objectMapper.readValue(response, new TypeReference<>() {});

    assertEquals(savedStock, stockList.get(stockList.size() - 1));
  }

  @Test
  @Order(3)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void getStockById() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/stocks/" + savedStock.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedStock.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value(savedStock.getSymbol()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(savedStock.getCompanyName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(savedStock.getPrice()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(savedStock.getAmount()));
  }

//  @Test
//  @Order(4)
//  @WithMockUser(username = "admin", roles = "ADMIN")
//  void updateStock() throws Exception {
//    RequestStockDataTransferObject requestBody = new RequestStockDataTransferObject("UPST3", "Updated Stock", 20.0, 2);
//    Stock updatedStock = new Stock(requestBody);
//
//    mockMvc.perform(MockMvcRequestBuilders.put("/stocks/" + savedStock.getId())
//            .content("{\"symbol\":\"UPST3\", \"companyName\":\"Updated Stock\", \"price\": 20.0. \"amount\":2}")
//            .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(MockMvcResultMatchers.status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedStock.getId()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value(updatedStock.getSymbol()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(updatedStock.getCompanyName()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updatedStock.getPrice()))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(updatedStock.getAmount()));
//  }

  @Test
  @Order(4)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void deleteStock() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/" + savedStock.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}

