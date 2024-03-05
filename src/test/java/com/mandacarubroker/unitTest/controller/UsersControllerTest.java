package com.mandacarubroker.unitTest.controller;

import com.mandacarubroker.controller.UsersController;
import com.mandacarubroker.domain.users.LoginDataTransferObject;
import com.mandacarubroker.domain.users.LoginResponseDataTransferObject;
import com.mandacarubroker.domain.users.RegisterDataTransferObject;
import com.mandacarubroker.domain.users.Users;
import com.mandacarubroker.repository.UsersRepository;
import com.mandacarubroker.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.anyString;

class UsersControllerTest {

  @Mock
  private UsersRepository usersRepository;

  @Mock
  private UsersService usersService;

  @InjectMocks
  private UsersController controller;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLogin_Success() {
    LoginDataTransferObject data = new LoginDataTransferObject("testuser","password");
    Users user = new Users();

    Mockito.when(usersService.get(data)).thenReturn(user);

    ResponseEntity<LoginResponseDataTransferObject> response = controller.login(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertNull(response.getBody().message());
    Assertions.assertEquals(user, response.getBody().data());
  }

  @Test
  void testLogin_UnregisteredUser() {
    LoginDataTransferObject data = new LoginDataTransferObject("testuser","password");

    Mockito.when(usersService.get(data)).thenReturn(null);

    ResponseEntity<LoginResponseDataTransferObject> response = controller.login(data);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("Unregistered user.", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testHandleBadCredentialsException() {
    BadCredentialsException ex = new BadCredentialsException("Invalid credentials");

    ResponseEntity<LoginResponseDataTransferObject> response = controller.handleBadCredentialsException(ex);

    Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("Invalid credentials", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testRegister_Success() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "testuser",
        "password",
        "test@example.com",
        "John",
        "Doe",
        new Timestamp(System.currentTimeMillis()),
        1000.0
    );

    Mockito.when(usersRepository.findUsersByUsername(anyString())).thenReturn(null);

    ResponseEntity<ResponseStatus> response = controller.register(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNull(response.getBody());
  }

  @Test
  void testRegister_Conflict() {
    RegisterDataTransferObject data = new RegisterDataTransferObject("testuser",
        "password",
        "test@example.com",
        "John",
        "Doe",
        new Timestamp(System.currentTimeMillis()),
        1000.0
    );

    Mockito.when(usersRepository.findUsersByUsername(anyString())).thenReturn(new Users());

    ResponseEntity<ResponseStatus> response = controller.register(data);

    Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    Assertions.assertNull(response.getBody());
  }

  @Test
  void testUpdate() {
    String id = "123456";
    RegisterDataTransferObject register1 = new RegisterDataTransferObject("testuser",
        "password",
        "test@example.com",
        "John",
        "Doe",
        new Timestamp(System.currentTimeMillis()),
        1000.0
    );
    RegisterDataTransferObject register2 = new RegisterDataTransferObject("testuser",
        "password2",
        "test2@example.com",
        "Joh",
        "De",
        new Timestamp(System.currentTimeMillis()),
        2000.0
    );

    Users data = new Users(register1, register1.password());
    Users updatedUser = new Users(register2, register2.password());

    Mockito.when(usersService.update(id, data)).thenReturn(Optional.of(updatedUser));

    Users response = controller.update(id, data);

    Assertions.assertEquals(updatedUser, response);
  }

  @Test
  void testUpdate_Null() {
    String id = "123456";
    Users data = new Users();

    Mockito.when(usersService.update(id, data)).thenReturn(Optional.empty());

    Users response = controller.update(id, data);

    Assertions.assertNull(response);
  }

  @Test
  void testDelete() {
    String id = "123456";

    controller.delete(id);

    Mockito.verify(usersService).delete(id);
  }
}

