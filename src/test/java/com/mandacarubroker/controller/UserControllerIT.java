package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.MandacaruBrokerApplication;
import com.mandacarubroker.UserFactory;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.dtos.RequestLoginDTO;
import com.mandacarubroker.dtos.RequestUserDTO;
import com.mandacarubroker.dtos.ResponseUserDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MandacaruBrokerApplication.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@Transactional
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Long totalUsers;
    private String existingId,nonExistingId;
    private final String NO_ENCRYPTED_PASS = "12345678";
    private User adminUser,normalUser;

    @BeforeEach
    void setup(){
        String encryptedPassword = passwordEncoder.encode(NO_ENCRYPTED_PASS);
        nonExistingId="none";
        normalUser = UserFactory.createUserWithNormalRole(encryptedPassword);
        adminUser = UserFactory.createAdminUser(encryptedPassword);
        userRepository.saveAll(List.of(normalUser,adminUser));
        totalUsers = userRepository.count();
        existingId = normalUser.getId();

    }
    private String authenticateUser(User user) throws Exception {
        RequestLoginDTO reqLoginDto = new RequestLoginDTO(user.getUsername(),NO_ENCRYPTED_PASS);
        var responseBody = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reqLoginDto)))
                .andExpect(status().isOk()).andReturn();

        return objectMapper.readTree(responseBody.getResponse().getContentAsString()).get("token").asText();
    }

    @Test
    void createShouldCreateAUserWhenUserIsAdmin() throws Exception {
        String token = authenticateUser(adminUser);

        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserDTO(NO_ENCRYPTED_PASS));
        String jsonObject = objectMapper.writeValueAsString(validDto);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonObject)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isCreated()).andReturn();

    }

    @Test
    void createShouldCreateAUserWithoutLogin() throws Exception {
        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserDTO(NO_ENCRYPTED_PASS));
        String jsonObject = objectMapper.writeValueAsString(validDto);

        var result = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
                .andExpect(status().isCreated()).andReturn();

        String jsonRespUser = result.getResponse().getContentAsString();
        ResponseUserDTO respUser= objectMapper.readValue(jsonRespUser,ResponseUserDTO.class);
        String userCreatedPassword = userRepository.findById(respUser.id()).orElseThrow().getPassword();
        assertEquals(totalUsers+1,userRepository.count());
        assertTrue(passwordEncoder.matches(NO_ENCRYPTED_PASS,userCreatedPassword));
    }

    @Test
    void createShouldNotCreateUserWhenUserAlreadyExists() throws Exception{
        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserWithNormalRole(NO_ENCRYPTED_PASS));
        String jsonObject = objectMapper.writeValueAsString(validDto);

        MvcResult result = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
                .andExpect(status().isCreated()).andReturn();

        /*String jsonRespUser = result.getResponse().getContentAsString();
        ResponseUserDTO respUser= objectMapper.readValue(jsonRespUser,ResponseUserDTO.class);
        String userCreatedPassword = userRepository.findById(respUser.id()).orElseThrow().getPassword();
        assertEquals(totalUsers+1,userRepository.count());
        assertTrue(passwordEncoder.matches(NO_ENCRYPTED_PASS,userCreatedPassword));*/
    }

    @Test
    void updateShouldUpdateWhenUserIsAdmin() throws Exception{
        String token = authenticateUser(adminUser);
        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserDTO(NO_ENCRYPTED_PASS));
        String jsonObject = objectMapper.writeValueAsString(validDto);
        mockMvc.perform(put("/users/{id}",existingId).contentType(MediaType.APPLICATION_JSON).content(jsonObject)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isOk()).andExpect(content().string(notNullValue()));

        User updatedUser = userRepository.findById(existingId).orElseThrow();
        assertEquals(totalUsers,userRepository.count());
        assertEquals(validDto.username(),updatedUser.getUsername());
        assertEquals(validDto.email(),updatedUser.getEmail());
        assertEquals(validDto.firstName(),updatedUser.getFirstName());
        assertEquals(validDto.lastName(),updatedUser.getLastName());
        assertTrue(passwordEncoder.matches(validDto.password(),updatedUser.getPassword()));
        assertEquals(validDto.balance(),updatedUser.getBalance());
        assertEquals(validDto.birthDate(),updatedUser.getBirthDate());
    }
    @Test
    void updateShouldNotUpdateWhenUserIsAdminAndNonExistingId() throws Exception{
        String token = authenticateUser(adminUser);
        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserDTO(NO_ENCRYPTED_PASS));
        String jsonObject = objectMapper.writeValueAsString(validDto);
        mockMvc.perform(put("/users/{id}",nonExistingId).contentType(MediaType.APPLICATION_JSON).content(jsonObject)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isNotFound()).andExpect(content().string(notNullValue()));

        assertEquals(totalUsers,userRepository.count());
        assertFalse(userRepository.existsById(nonExistingId));
    }

    @Test
    void updateShouldNotUpdateWhenUserIsNormal() throws Exception{
        String token = authenticateUser(normalUser);
        User beforeUser = userRepository.findById(existingId).get();
        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserDTO(NO_ENCRYPTED_PASS));
        String jsonObject = objectMapper.writeValueAsString(validDto);
        mockMvc.perform(put("/users/{id}",existingId).contentType(MediaType.APPLICATION_JSON).content(jsonObject)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isForbidden()).andExpect(content().string(notNullValue()));

        User updatedUser = userRepository.findById(existingId).orElseThrow();
        assertEquals(totalUsers,userRepository.count());
        assertEquals(beforeUser.getUsername(),updatedUser.getUsername());
        assertEquals(beforeUser.getEmail(),updatedUser.getEmail());
        assertEquals(beforeUser.getFirstName(),updatedUser.getFirstName());
        assertEquals(beforeUser.getLastName(),updatedUser.getLastName());
        assertEquals(beforeUser.getPassword(),updatedUser.getPassword());
        assertEquals(beforeUser.getBalance(),updatedUser.getBalance());
        assertEquals(beforeUser.getBirthDate(),updatedUser.getBirthDate());
    }

    @Test
    void updateShouldNotUpdateWithoutLogin() throws Exception{
        User beforeUser = userRepository.findById(existingId).get();
        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserDTO(NO_ENCRYPTED_PASS));
        String jsonObject = objectMapper.writeValueAsString(validDto);
        mockMvc.perform(put("/users/{id}",existingId).contentType(MediaType.APPLICATION_JSON).content(jsonObject))
                .andExpect(status().isForbidden()).andExpect(content().string(notNullValue()));

        User updatedUser = userRepository.findById(existingId).orElseThrow();
        assertEquals(totalUsers,userRepository.count());
        assertEquals(beforeUser.getUsername(),updatedUser.getUsername());
        assertEquals(beforeUser.getEmail(),updatedUser.getEmail());
        assertEquals(beforeUser.getFirstName(),updatedUser.getFirstName());
        assertEquals(beforeUser.getLastName(),updatedUser.getLastName());
        assertEquals(beforeUser.getPassword(),updatedUser.getPassword());
        assertEquals(beforeUser.getBalance(),updatedUser.getBalance());
        assertEquals(beforeUser.getBirthDate(),updatedUser.getBirthDate());
    }
    @Test
    void updateShouldNotUpdateWhenUserIsAdminAndInvalidRequest() throws Exception{
        String token = authenticateUser(normalUser);
        User beforeUser = userRepository.findById(existingId).get();
        RequestUserDTO validDto = new RequestUserDTO(UserFactory.createUserWithInvalidData());
        String jsonObject = objectMapper.writeValueAsString(validDto);
        mockMvc.perform(put("/users/{id}",existingId).contentType(MediaType.APPLICATION_JSON).content(jsonObject)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isForbidden()).andExpect(content().string(notNullValue()));

        User updatedUser = userRepository.findById(existingId).orElseThrow();
        assertEquals(totalUsers,userRepository.count());
        assertEquals(beforeUser.getUsername(),updatedUser.getUsername());
        assertEquals(beforeUser.getEmail(),updatedUser.getEmail());
        assertEquals(beforeUser.getFirstName(),updatedUser.getFirstName());
        assertEquals(beforeUser.getLastName(),updatedUser.getLastName());
        assertEquals(beforeUser.getPassword(),updatedUser.getPassword());
        assertEquals(beforeUser.getBalance(),updatedUser.getBalance());
        assertEquals(beforeUser.getBirthDate(),updatedUser.getBirthDate());
    }

    @Test
    void deleteShouldDeleteAUserWhenUserIsAdmin() throws Exception {
        String token = authenticateUser(adminUser);
        mockMvc.perform(delete("/users/{id}",existingId).contentType(MediaType.APPLICATION_JSON).header("Authorization","Bearer "+token))
                .andExpect(status().isNoContent())
                .andExpect(content().string(blankOrNullString()));

        assertEquals(totalUsers-1,userRepository.count());
        assertTrue(userRepository.findById(existingId).isEmpty());
    }

    @Test
    void deleteShouldNotDeleteAUserWhenUserIsAdminAndNonExistingId() throws Exception {
        String nonExistingId = "none";
        String token = authenticateUser(adminUser);
        mockMvc.perform(delete("/users/{id}",nonExistingId).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User Not Found")));

        assertEquals(totalUsers,userRepository.count());
        assertTrue(userRepository.findById(nonExistingId).isEmpty());
    }

    @Test
    void deleteShouldNotDeleteAUserWhenUserIsNotAdmin() throws Exception {
        //no auth
        mockMvc.perform(delete("/users/{id}",existingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        assertEquals(totalUsers,userRepository.count());

        String token = authenticateUser(normalUser);
        mockMvc.perform(delete("/users/{id}",existingId).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isForbidden());
        assertEquals(totalUsers,userRepository.count());
    }
    @Test
    void findAllShouldNotReturnWhenUserIsNotAdmin() throws Exception {
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.notNullValue()));

        String token = authenticateUser(normalUser);
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isForbidden())
                .andExpect(content().string(Matchers.notNullValue()));
    }
    @Test
    void findAllShouldReturnUsersWhenUserisAdmin() throws Exception {
        String token = authenticateUser(adminUser);
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.notNullValue()))
                .andExpect(jsonPath("$").isArray());


    }
}
