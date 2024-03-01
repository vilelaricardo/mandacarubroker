package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mandacarubroker.domain.user.RequestUserDTO;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.mandacarubroker.domain.user.Permission.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService service;

    private final String validEmail = "lara.souza@gmail.com";
    private final String validUsername = "LaraS";
    private final String validPassword = "pass555";
    private final String validFirstName = "Lara";
    private final String validLastName = "Souza";
    private final LocalDate validBirthDate = LocalDate.of(1997,4,5);
    private final double validBalance = 90.50;

    private final RequestUserDTO validUserDTO = new RequestUserDTO(
            validEmail,
            validUsername,
            validPassword,
            validFirstName,
            validLastName,
            validBirthDate,
            validBalance
    );

    private final String urlRequestInvalidUser = "/users/dummy-user-id";
    private User user;
    private String userId;
    private String urlRequestUserById;

    @BeforeEach
    void setUp() {
        Collection<SimpleGrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority(USER_CREATE.getPermission()),
                new SimpleGrantedAuthority(USER_READ.getPermission()),
                new SimpleGrantedAuthority(USER_UPDATE.getPermission()),
                new SimpleGrantedAuthority(USER_DELETE.getPermission())
        );
        Authentication authentication = new TestingAuthenticationToken("user", "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        user = service.getAllUsers().get(0);
        userId = user.getId();
        urlRequestUserById = "/users/" + userId;
    }

    @AfterEach
    void tearDown() {
    }

    void assertRequestDTOEqualsUser(final RequestUserDTO userDTO, final User receivedUser) {
        assertEquals(userDTO.email(), receivedUser.getEmail());
        assertEquals(userDTO.username(), receivedUser.getUsername());
        assertEquals(userDTO.firstName(), receivedUser.getFirstName());
        assertEquals(userDTO.lastName(), receivedUser.getLastName());
        assertEquals(userDTO.birthDate(), receivedUser.getBirthDate());
        assertEquals(userDTO.balance(), receivedUser.getBalance());
    }


    @Test
    void itShouldReturnOkStatusWhenGetAllUsers() throws Exception {
        RequestBuilder requestBuilder = get("/users");
        ResultMatcher resultMatcher = status().isOk();
        mockMvc.perform(requestBuilder).andExpect(resultMatcher);
    }

    @Test
    void itShouldBeAbleToGetAllUsers() throws Exception {
        RequestBuilder requestBuilder = get("/users");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        List<User> users = objectMapper.readValue(content, List.class);

        assertEquals(service.getAllUsers().size(), users.size());
    }

    @Test
    void itShouldBeAbleToGetUserById() throws Exception {
        String userJsonString = objectMapper.writeValueAsString(user);

        RequestBuilder requestBuilder = get(urlRequestUserById);
        ResultMatcher matchResponse = content().json(userJsonString);

        mockMvc.perform(requestBuilder).andExpectAll(matchResponse);
    }

    @Test
    void itShouldReturnOkStatusWhenGetUserById() throws Exception {
        RequestBuilder requestBuilder = get(urlRequestUserById);
        ResultMatcher matchStatus = status().isOk();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnNotFoundWhenGetUserDoesNotExists() throws Exception {
        RequestBuilder requestBuilder = get(urlRequestInvalidUser);
        ResultMatcher matchStatus = status().isNotFound();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnCreatedStatusAfterSucessfulPost() throws Exception {
        String userJsonString = objectMapper.writeValueAsString(validUserDTO);

        RequestBuilder requestBuilder = post("/users")
                .contentType("application/json")
                .content(userJsonString);
        ResultMatcher matchStatus = status().isCreated();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    // Como o "authorities" não é uma propriedade de User,
    // não é possível mapear o JSON para um objeto User.
    // Isso deve ser resolvido ao criar um ResponseDTO para a entidade User.
    void itShouldReturnUserDataAfterSucessfulPost() throws Exception {
        String userJsonString = objectMapper.writeValueAsString(validUserDTO);
        System.out.println(userJsonString);
        RequestBuilder requestBuilder = post("/users")
                .contentType("application/json")
                .content(userJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
        User createdUser = objectMapper.readValue(content, User.class);

        assertRequestDTOEqualsUser(validUserDTO, createdUser);
    }

    // Como o "authorities" não é uma propriedade de User,
    // não é possível mapear o JSON para um objeto User.
    // Isso deve ser resolvido ao criar um ResponseDTO para a entidade User.
    void itShouldReturnUserDataAfterSucessfulPut() throws Exception {
        String userJsonString = objectMapper.writeValueAsString(validUserDTO);

        RequestBuilder requestBuilder = put(urlRequestUserById)
                .contentType("application/json")
                .content(userJsonString);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String content = result.getResponse().getContentAsString();
        User updatedUser = objectMapper.readValue(content, User.class);
        assertRequestDTOEqualsUser(validUserDTO, updatedUser);
    }

    @Test
    void itShouldReturnOkStatusAfterSucessfulPut() throws Exception {
        String userJsonString = objectMapper.writeValueAsString(validUserDTO);

        RequestBuilder requestBuilder = put(urlRequestUserById)
                .contentType("application/json")
                .content(userJsonString);
        ResultMatcher matchStatus = status().isOk();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnNotFoundWhenPutUserDoesNotExists() throws Exception {
        String userJsonString = objectMapper.writeValueAsString(validUserDTO);

        RequestBuilder requestBuilder = put(urlRequestInvalidUser)
                .contentType("application/json")
                .content(userJsonString);
        ResultMatcher matchStatus = status().isNotFound();

        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldBeAbleToDeleteUser() throws Exception {
        RequestBuilder requestBuilder = delete(urlRequestUserById);
        ResultMatcher matchStatus = status().isNoContent();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
        assertEquals(Optional.empty(), service.getUserById(userId));
    }

    @Test
    void itShouldReturnNoContentStatusWhenDeleteUser() throws Exception {
        RequestBuilder requestBuilder = delete(urlRequestUserById);
        ResultMatcher matchStatus = status().isNoContent();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }

    @Test
    void itShouldReturnNoContentStatusWhenDeleteUserDoesNotExists() throws Exception {
        RequestBuilder requestBuilder = delete(urlRequestInvalidUser);
        ResultMatcher matchStatus = status().isNoContent();
        mockMvc.perform(requestBuilder).andExpect(matchStatus);
    }
}