package com.mandacarubroker.unitTest.service;

import com.mandacarubroker.domain.authuser.AuthUser;
import com.mandacarubroker.domain.authuser.AuthUserRole;
import com.mandacarubroker.domain.authuser.RegisterDataTransferObject;
import com.mandacarubroker.repository.AuthUserRepository;
import com.mandacarubroker.service.AuthorizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.anyString;

class AuthorizationServiceTest {

  @Mock
  private AuthUserRepository authUserRepository;

  @InjectMocks
  private AuthorizationService authorizationService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLoadUserByUsername() {
    AuthUser mockUser = new AuthUser("testuser", "hashedpassword", AuthUserRole.USER);
    Mockito.when(authUserRepository.findByUsername(anyString())).thenReturn(mockUser);

    UserDetails userDetails = authorizationService.loadUserByUsername("testuser");

    Assertions.assertEquals(mockUser.getUsername(), userDetails.getUsername());
    Assertions.assertEquals(mockUser.getPassword(), userDetails.getPassword());
  }

  @Test
  void testLoadUserByUsername_UsernameNotFoundException() {
    Mockito.when(authUserRepository.findByUsername(anyString())).thenReturn(null);

    Assertions.assertThrows(UsernameNotFoundException.class, () -> {
      authorizationService.loadUserByUsername("nonexistentuser");
    });
  }

  @Test
  void testCreate() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "testuser",
        "password123",
        AuthUserRole.USER
    );

    authorizationService.create(data);

    Mockito.verify(authUserRepository, Mockito.times(1)).save(Mockito.any(AuthUser.class));
  }

  @Test
  void testDelete() {
    String id = "123456";

    authorizationService.delete(id);

    Mockito.verify(authUserRepository, Mockito.times(1)).deleteById(id);
  }
}
