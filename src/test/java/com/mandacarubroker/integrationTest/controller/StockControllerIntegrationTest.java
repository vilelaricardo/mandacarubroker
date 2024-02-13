package com.mandacarubroker.integrationTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import com.mandacarubroker.domain.stock.Stock;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  private static Stock savedStock;

  @AfterAll
  static void setUp() {
    savedStock = null;
  }

  @Test
  @Order(1)
  void createStock() throws Exception {
    RequestStockDataTransferObject requestData = new RequestStockDataTransferObject("STOK1", "Test s.a", 10.0);
    Stock stock = new Stock(requestData);

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/stocks")
      .content("{\"symbol\":\"STOK1\", \"companyName\":\"Test s.a\", \"price\":10.0}")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value(stock.getSymbol()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(stock.getCompanyName()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(stock.getPrice()))
      .andReturn();


    // Save the request result to use in future tests
    String responseBody = result.getResponse().getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});

    String id = (String) responseMap.get("id");
    String symbol = (String) responseMap.get("symbol");
    String companyName = (String) responseMap.get("companyName");
    Double price = (Double) responseMap.get("price");

    savedStock = new Stock(id, symbol, companyName, price);
  }

  @Test
  @Order(2)
  void getAllStocks() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/stocks")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(savedStock.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].symbol").value(savedStock.getSymbol()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value(savedStock.getCompanyName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(savedStock.getPrice()));
  }

  @Test
  @Order(3)
  void getStockById() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/stocks/" + savedStock.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedStock.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value(savedStock.getSymbol()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(savedStock.getCompanyName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(savedStock.getPrice()));
  }



  @Test
  @Order(4)
  void updateStock() throws Exception {
    Stock updatedStock = new Stock(savedStock.getId(), "UPST3", "Updated Stock", 20.0);

    mockMvc.perform(MockMvcRequestBuilders.put("/stocks/" + savedStock.getId())
            .content("{\"symbol\":\"UPST3\", \"companyName\":\"Updated Stock\", \"price\": 20.0}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedStock.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value(updatedStock.getSymbol()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(updatedStock.getCompanyName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updatedStock.getPrice()));
  }

  @Test
  @Order(5)
  void deleteStock() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/" + savedStock.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}

