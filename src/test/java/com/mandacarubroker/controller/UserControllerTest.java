package com.mandacarubroker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.UserFactory;
import com.mandacarubroker.controller.exceptions.StandardError;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.dtos.RequestUserDTO;
import com.mandacarubroker.dtos.ResponseUserDTO;
import com.mandacarubroker.service.TokenService;
import com.mandacarubroker.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.Instant;
import java.util.List;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
//@ExtendWith({SpringExtension.class, MockitoExtension.class})
class UserControllerTest {

    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private RequestUserDTO validRequestUserDto;
    private ResponseUserDTO validResponseUserDto;
    private String existingId,nonExistingId;
    private static final String USERS_URL = "/users";
    private static final String NOT_FOUND_MSG = "User Not Found";
    private StandardError errorNotFound;
    @BeforeEach
    void setup(){
        User user = UserFactory.createUserDTO("123456");
        user.setId("1");
        validRequestUserDto = new RequestUserDTO(user.getUsername(), user.getPassword()
                , user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getBalance());
        validResponseUserDto = new ResponseUserDTO(user);
        existingId = "1";
        nonExistingId = "null";

        errorNotFound = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(),"Entity Not Found",NOT_FOUND_MSG,
                UriComponentsBuilder.fromPath(USERS_URL+"/{id}").buildAndExpand(nonExistingId).toUriString());
    }

    @Test
    void getAllStocksShouldReturnStatusOK() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(validResponseUserDto));

        mockMvc.perform(get(USERS_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(validResponseUserDto.id()))
                .andExpect(jsonPath("$[0].username").value(validResponseUserDto.username()))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }

    @Test
    void findByIdShouldReturnStatusOK() throws Exception {
        when(userService.getUserById(existingId)).thenReturn(validResponseUserDto);
        mockMvc.perform(get(USERS_URL+"/{id}",existingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(validResponseUserDto.id()))
                .andExpect(jsonPath("$.username").value(validResponseUserDto.username()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void findByIdShouldReturnNotFoundStatus() throws Exception {
        when(userService.getUserById(nonExistingId)).thenThrow(new EntityNotFoundException(NOT_FOUND_MSG));
        mockMvc.perform(get(USERS_URL+"/{id}",nonExistingId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instant").exists())
                .andExpect(jsonPath("$.status").value(errorNotFound.status()))
                .andExpect(jsonPath("$.error").value(errorNotFound.error()))
                .andExpect(jsonPath("$.message").value(errorNotFound.message()))
                .andExpect(jsonPath("$.path").value(errorNotFound.path()));
    }

    @Test
    void createShouldReturnStatusCreated() throws Exception {
        String jsonObject = objectMapper.writeValueAsString(validRequestUserDto);
        when(userService.createUser(validRequestUserDto)).thenReturn(validResponseUserDto);

        mockMvc.perform(post(USERS_URL).content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(header().stringValues("location","/users/1"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(validResponseUserDto.id()))
                .andExpect(jsonPath("$.username").value(validResponseUserDto.username()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void updateShouldReturnOKStatus() throws Exception {
        String jsonObject = objectMapper.writeValueAsString(validRequestUserDto);
        when(userService.updateUser(existingId,validRequestUserDto)).thenReturn(validResponseUserDto);

        mockMvc.perform(put(USERS_URL+"/{id}",existingId).content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.username").value(validResponseUserDto.username()))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void updateShouldReturnNotFoundStatusWhenNonExistingIdAndValidRequest() throws Exception {
        String jsonObject = objectMapper.writeValueAsString(validRequestUserDto);
        when(userService.updateUser(nonExistingId,validRequestUserDto)).thenThrow(new EntityNotFoundException(NOT_FOUND_MSG));

        mockMvc.perform(put(USERS_URL+"/{id}",nonExistingId).content(jsonObject).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instant").exists())
                .andExpect(jsonPath("$.status").value(errorNotFound.status()))
                .andExpect(jsonPath("$.error").value(errorNotFound.error()))
                .andExpect(jsonPath("$.message").value(errorNotFound.message()))
                .andExpect(jsonPath("$.path").value(errorNotFound.path()));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(userService).deleteUser(existingId);
        mockMvc.perform(delete(USERS_URL+"/{id}",existingId))
                .andExpect(status().isNoContent())
                .andExpect(content().string(blankOrNullString()));
    }

    @Test
    void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception {
        doThrow(new EntityNotFoundException(NOT_FOUND_MSG)).when(userService).deleteUser(nonExistingId);
        mockMvc.perform(delete(USERS_URL+"/{id}",nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.instant").exists())
                .andExpect(jsonPath("$.status").value(errorNotFound.status()))
                .andExpect(jsonPath("$.error").value(errorNotFound.error()))
                .andExpect(jsonPath("$.message").value(errorNotFound.message()))
                .andExpect(jsonPath("$.path").value(errorNotFound.path()));
    }
}
