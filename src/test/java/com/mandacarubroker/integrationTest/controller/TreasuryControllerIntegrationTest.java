package com.mandacarubroker.integrationTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.treasury.GetResponseDataTransferObject;
import com.mandacarubroker.domain.treasury.RegisterDataTransferObject;
import com.mandacarubroker.domain.treasury.ResponseDataTransferObject;
import com.mandacarubroker.domain.treasury.Treasury;
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

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TreasuryControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static Treasury register;

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
        100,
        "type a",
        new Timestamp(16872635),
        10.4f,
        10.2f
    );
    Treasury data = new Treasury(requestData);

    String result = mockMvc.perform(MockMvcRequestBuilders.post("/treasury/register")
            .content("{\"name\":\"test\",\"amount\":100,\"type\":\"type a\",\"maturityDate\":16872635, \"interestRate\":10.4, \"price\":10.2}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(data.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.interestRate").value(data.getInterestRate()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.type").value(data.getType()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.amount").value(data.getAmount()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").value(data.getPrice()))
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Save the request result to use in future tests
    ResponseDataTransferObject responseBody = objectMapper.readValue(result, ResponseDataTransferObject.class);
    register = objectMapper.convertValue(responseBody.data(), Treasury.class);
  }

  @Test
  @Order(2)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void getAll() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders.get("/treasury")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andReturn()
        .getResponse()
        .getContentAsString();

    GetResponseDataTransferObject responseBody = objectMapper.readValue(response, GetResponseDataTransferObject.class);
    List<Treasury> ETFList = objectMapper.convertValue(responseBody.data(), new TypeReference<>() {});

    assertEquals(register, ETFList.get(ETFList.size() - 1));
  }

  @Test
  @Order(3)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void get() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders.get("/treasury/" + register.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andReturn()
        .getResponse()
        .getContentAsString();

    ResponseDataTransferObject responseBody = objectMapper.readValue(response, ResponseDataTransferObject.class);
    Treasury bodyData = objectMapper.convertValue(responseBody.data(), Treasury.class);

    assertEquals(register, bodyData);
  }
  @Test
  @Order(4)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void updateStock() throws Exception {
    RegisterDataTransferObject requestBody = new RegisterDataTransferObject(
        "UPST3",
        100,
        "New type",
        new Timestamp(16872635),
        20.4f,
        20.2f
    );

    mockMvc.perform(MockMvcRequestBuilders.put("/treasury/" + register.getId())
            .content("{\"name\":\"UPST3\",\"amount\":100,\"type\":\"New type\",\"maturityDate\":16872635, \"interestRate\":20.4, \"price\":20.2}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(register.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.interestRate").value(requestBody.interestRate()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.type").value(requestBody.type()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").value(requestBody.price()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.amount").value(requestBody.amount()));
  }

  @Test
  @Order(5)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void delete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/treasury/" + register.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
