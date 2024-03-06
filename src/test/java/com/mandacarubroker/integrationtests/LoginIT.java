package com.mandacarubroker.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.MandacaruBrokerApplication;
import com.mandacarubroker.UserFactory;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.dtos.RequestLoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MandacaruBrokerApplication.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@Transactional
class LoginIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void loginShouldReturnAToken() throws Exception {
        userRepository.save(UserFactory.createUserWithNormalRole(passwordEncoder.encode("12345678")));
        RequestLoginDTO reqLoginDto = new RequestLoginDTO("paulo","12345678");
        var responseBody = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reqLoginDto)))
                .andExpect(status().isOk()).andReturn();
        String token =  objectMapper.readTree(responseBody.getResponse().getContentAsString()).get("token").asText();
        assertFalse(token.isBlank());
    }

    @Test
    void loginShouldReturnUnauthorizedAndNotReturnAToken() throws Exception {
        RequestLoginDTO reqLoginDto = new RequestLoginDTO("paulo","12345678");
        var responseBody = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reqLoginDto)))
                .andExpect(status().isUnauthorized()).andReturn();
        assertNull(objectMapper.readTree(responseBody.getResponse().getContentAsString()).get("token"));
    }
}
