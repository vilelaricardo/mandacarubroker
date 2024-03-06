package com.mandacarubroker.unitTest.controller;

import com.mandacarubroker.controller.AuthenticationController;
import com.mandacarubroker.domain.authuser.*;
import com.mandacarubroker.infra.security.TokenService;
import com.mandacarubroker.repository.AuthUserRepository;
import com.mandacarubroker.service.AuthorizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class AuthenticationControllerTest {

  @Mock
  private AuthUserRepository authUserRepository;

  @Mock
  private AuthorizationService authorizationService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private TokenService tokenService;

  @InjectMocks
  private AuthenticationController authenticationController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLogin_ValidCredentials() {
    AuthenticationDataTransferObject data = new AuthenticationDataTransferObject("testuser", "password123");

    Mockito.when(authenticationManager.authenticate(any())).thenReturn(Mockito.mock(Authentication.class));
    Mockito.when(tokenService.generateSystemToken(any(AuthUser.class))).thenReturn("generatedToken");

    ResponseEntity<LoginResponseDataTransferObject> response = authenticationController.login(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertNotNull(response.getBody().token());
  }

  @Test
  void testLogin_InvalidCredentials() {
    AuthenticationDataTransferObject data = new AuthenticationDataTransferObject("testuser", "wrongpassword");

    Mockito.when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

    ResponseEntity<LoginResponseDataTransferObject> response = authenticationController.login(data);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assertions.assertNull(response.getBody());
  }

  @Test
  void testRegister_NewUser() {
    RegisterDataTransferObject data = new RegisterDataTransferObject("newuser", "password", AuthUserRole.USER);

    Mockito.when(authUserRepository.findByUsername(anyString())).thenReturn(null);

    ResponseEntity<ResponseStatus> response = authenticationController.register(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testRegister_ExistingUser() {
    RegisterDataTransferObject data = new RegisterDataTransferObject("existinguser", "password", AuthUserRole.USER);

    Mockito.when(authUserRepository.findByUsername(anyString())).thenReturn(new AuthUser());

    ResponseEntity<ResponseStatus> response = authenticationController.register(data);

    Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void testDeleteAuthUser() {
    String userId = "123456";

    authenticationController.deleteAuthUser(userId);

    Mockito.verify(authorizationService, Mockito.times(1)).delete(userId);
  }
}

