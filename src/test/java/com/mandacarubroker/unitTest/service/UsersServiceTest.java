package com.mandacarubroker.unitTest.service;

import com.mandacarubroker.domain.users.LoginDataTransferObject;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class UsersServiceTest {

  @Mock
  private UsersRepository usersRepository;

  @InjectMocks
  private UsersService usersService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreate() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "testuser",
        "passwor",
        "test@example.com",
        "John",
        "Doe",
        new Timestamp(System.currentTimeMillis()),
        1000.0
    );

    usersService.create(data);

    Mockito.verify(usersRepository, Mockito.times(1)).save(any(Users.class));
  }

  @Test
  void testGet_ValidCredentials() {
    LoginDataTransferObject data = new LoginDataTransferObject("testuser", "password123");

    Users user = new Users();
    user.setUsername("testuser");
    String encryptedPassword = new BCryptPasswordEncoder().encode("password123");
    user.setPassword(encryptedPassword);

    Mockito.when(usersRepository.findUsersByUsername(anyString())).thenReturn(user);

    Assertions.assertDoesNotThrow(() -> {
      Users result = usersService.get(data);
      Assertions.assertEquals(user, result);
    });
  }

  @Test
  void testGet_InvalidCredentials() {
    LoginDataTransferObject data = new LoginDataTransferObject("testuser", "wrongpassword");

    Users user = new Users();
    user.setUsername("testuser");
    String encryptedPassword = new BCryptPasswordEncoder().encode("password123");
    user.setPassword(encryptedPassword);

    Mockito.when(usersRepository.findUsersByUsername(anyString())).thenReturn(user);

    Assertions.assertThrows(BadCredentialsException.class, () -> {
      usersService.get(data);
    });
  }

  @Test
  void testUpdate() {
    String id = "123456";
    RegisterDataTransferObject updatedData = new RegisterDataTransferObject(
        "updateduser",
        "updatedpassword",
        "updated@example.com",
        "Updated",
        "User",
        new Timestamp(System.currentTimeMillis()),
        2000.0
    );

    Users user = new Users();
    Mockito.when(usersRepository.findById(id)).thenReturn(Optional.of(user));

    usersService.update(id, updatedData);

    Mockito.verify(usersRepository, Mockito.times(1)).save(any(Users.class));
  }

  @Test
  void testDelete() {
    String id = "123456";

    usersService.delete(id);

    Mockito.verify(usersRepository, Mockito.times(1)).deleteById(id);
  }
}
