package com.mandacarubroker.integrationTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.typesinvestiments.GetResponseDataTransferObject;
import com.mandacarubroker.domain.typesinvestiments.RegisterTypeInvestment;
import com.mandacarubroker.domain.typesinvestiments.ResponseDataTransferObject;
import com.mandacarubroker.domain.typesinvestiments.TypesInvestments;
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
class InvestmentsControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static TypesInvestments register;

  @AfterAll
  static void setUp() {
    register = null;
  }

  @Test
  @Order(1)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void register() throws Exception {
    RegisterTypeInvestment requestData = new RegisterTypeInvestment("Test");
    TypesInvestments etf = new TypesInvestments(requestData);

    String result = mockMvc.perform(MockMvcRequestBuilders.post("/assets/register")
            .content("{\"name\":\"Test\"}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(etf.getName()))
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Save the request result to use in future tests
    ResponseDataTransferObject responseBody = objectMapper.readValue(result, ResponseDataTransferObject.class);
    register = objectMapper.convertValue(responseBody.data(), TypesInvestments.class);
  }

  @Test
  @Order(2)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void getAll() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders.get("/assets")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andReturn()
        .getResponse()
        .getContentAsString();

    GetResponseDataTransferObject responseBody = objectMapper.readValue(response, GetResponseDataTransferObject.class);
    List<TypesInvestments> ETFList = objectMapper.convertValue(responseBody.data(), new TypeReference<>() {});

    assertEquals(register, ETFList.get(ETFList.size() - 1));
  }

  @Test
  @Order(3)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void delete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/assets/" + register.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}