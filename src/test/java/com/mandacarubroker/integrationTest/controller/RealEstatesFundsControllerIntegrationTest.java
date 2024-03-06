package com.mandacarubroker.integrationTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.realestatesfunds.GetResponseDataTransferObject;
import com.mandacarubroker.domain.realestatesfunds.RealEstatesFunds;
import com.mandacarubroker.domain.realestatesfunds.RegisterDataTransferObject;
import com.mandacarubroker.domain.realestatesfunds.ResponseDataTransferObject;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RealEstatesFundsControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static RealEstatesFunds register;

  @AfterAll
  static void setUp() {
    register = null;
  }

  @Test
  @Order(1)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void register() throws Exception {
    RegisterDataTransferObject requestData = new RegisterDataTransferObject(
        "test",
        "STOK1",
        "Test s.a",
        "test",
        10,
        10.2f
    );
    RealEstatesFunds data = new RealEstatesFunds(requestData);

    String result = mockMvc.perform(MockMvcRequestBuilders.post("/ref/register")
            .content("{\"name\":\"test\",\"symbol\":\"STOK1\",\"companyName\":\"Test s.a\",\"type\":\"test\", \"price\":10.2, \"amount\":10}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.symbol").value(data.getSymbol()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(data.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.companyName").value(data.getCompanyName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.type").value(data.getType()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.amount").value(data.getAmount()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").value(data.getPrice()))
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Save the request result to use in future tests
    ResponseDataTransferObject responseBody = objectMapper.readValue(result, ResponseDataTransferObject.class);
    register = objectMapper.convertValue(responseBody.data(), RealEstatesFunds.class);
  }

  @Test
  @Order(2)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void getAll() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders.get("/ref")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andReturn()
        .getResponse()
        .getContentAsString();

    GetResponseDataTransferObject responseBody = objectMapper.readValue(response, GetResponseDataTransferObject.class);
    List<RealEstatesFunds> ETFList = objectMapper.convertValue(responseBody.data(), new TypeReference<>() {});

    assertEquals(register, ETFList.get(ETFList.size() - 1));
  }

  @Test
  @Order(3)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void get() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders.get("/ref/" + register.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andReturn()
        .getResponse()
        .getContentAsString();

    ResponseDataTransferObject responseBody = objectMapper.readValue(response, ResponseDataTransferObject.class);
    RealEstatesFunds bodyData = objectMapper.convertValue(responseBody.data(), RealEstatesFunds.class);

    assertEquals(register, bodyData);
  }
  @Test
  @Order(4)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void updateStock() throws Exception {
    RegisterDataTransferObject requestBody = new RegisterDataTransferObject(
        "UPST3",
        "Updated Stock",
        "New company",
        "test",
        20,
        20.3f
    );

    mockMvc.perform(MockMvcRequestBuilders.put("/ref/" + register.getId())
            .content("{\"name\":\"UPST3\", \"symbol\":\"Updated Stock\", \"companyName\":\"New company\", \"type\": \"test\",  \"price\": 20.3, \"amount\":20}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(register.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.symbol").value(requestBody.symbol()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.companyName").value(requestBody.companyName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.type").value(requestBody.type()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").value(requestBody.price()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.amount").value(requestBody.amount()));
  }

  @Test
  @Order(5)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void delete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/ref/" + register.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
