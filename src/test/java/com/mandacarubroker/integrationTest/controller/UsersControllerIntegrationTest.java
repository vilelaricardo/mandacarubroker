package com.mandacarubroker.integrationTest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.domain.users.LoginDataTransferObject;
import com.mandacarubroker.domain.users.LoginResponseDataTransferObject;
import com.mandacarubroker.domain.users.RegisterDataTransferObject;
import com.mandacarubroker.domain.users.Users;
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
class UsersControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static Users register;

  @AfterAll
  static void setUp() {
    register = null;
  }

  @Test
  @Order(1)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void register() throws Exception {
    RegisterDataTransferObject requestData = new RegisterDataTransferObject(
        "etf test",
        "STOK1",
        "Test s.a",
        "user",
        "test",
        new Timestamp(1687232),
        10.2
    );
    Users etf = new Users(requestData, requestData.password());

    mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
            .content("{\"username\":\"etf test\",\"password\":\"STOK1\",\"email\":\"Test s.a\",\"firstName\":\"user\", \"lastName\":\"name\", \"amount\":10,\"birthData\": 1687232, \"balance\": 10.2}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @Order(2)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void login() throws Exception {
    LoginDataTransferObject requestData = new LoginDataTransferObject("etf test", "STOK1");

    var response = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
            .content("{\"username\":\"etf test\",\"password\":\"STOK1\"}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value(requestData.username()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").exists())
        .andReturn()
        .getResponse()
        .getContentAsString();

    LoginResponseDataTransferObject responseBody = objectMapper.readValue(response, LoginResponseDataTransferObject.class);
    register = objectMapper.convertValue(responseBody.data(), Users.class);
  }

  @Test
  @Order(3)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void update() throws Exception {
    RegisterDataTransferObject requestData = new RegisterDataTransferObject(
        "test",
        "STOK1",
        "Test",
        "user",
        "test",
        new Timestamp(1687232),
        10.2
    );

    mockMvc.perform(MockMvcRequestBuilders.put("/user/" + register.getId())
            .content("{\"username\":\"test\",\"password\":\"STOK1\",\"email\":\"Test\",\"firstName\":\"user\", \"lastName\":\"test\", \"amount\":10,\"birthData\": 1687232, \"balance\": 10.2}")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(register.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(requestData.username()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(requestData.email()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(requestData.firstName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(requestData.lastName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(requestData.balance()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.birthData").isNotEmpty());
  }

  @Test
  @Order(4)
  @WithMockUser(username = "admin", roles = "ADMIN")
  void delete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + register.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}

