package com.mandacarubroker.unitTest.domain.users;

import com.mandacarubroker.domain.users.RegisterDataTransferObject;
import com.mandacarubroker.domain.users.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class UsersTest {
  private Users user;

  @BeforeEach
  public void setUp() {
    user = new Users();
  }

  @Test
  void testSetUsername() {
    user.setUsername("testuser");
    Assertions.assertEquals("testuser", user.getUsername());
  }

  @Test
  void testSetPassword() {
    user.setPassword("password123");
    Assertions.assertEquals("password123", user.getPassword());
  }

  @Test
  void testSetEmail() {
    user.setEmail("test@example.com");
    Assertions.assertEquals("test@example.com", user.getEmail());
  }

  @Test
  void testSetFirstName() {
    user.setFirstName("John");
    Assertions.assertEquals("John", user.getFirstName());
  }

  @Test
  void testSetLastName() {
    user.setLastName("Doe");
    Assertions.assertEquals("Doe", user.getLastName());
  }

  @Test
  void testSetBirthData() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    user.setBirthData(timestamp);
    Assertions.assertEquals(timestamp, user.getBirthData());
  }

  @Test
  void testSetBalance() {
    user.setBalance(1000.0);
    Assertions.assertEquals(1000.0, user.getBalance());
  }

  @Test
  void testConstructorWithDataTransferObject() {
    String password = "password123";
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "testuser",
        password,
        "test@example.com",
        "John",
        "Doe",
        new Timestamp(System.currentTimeMillis()),
        1000.0
    );

    Users userWithDto = new Users(data, password);

    Assertions.assertEquals(data.username(), userWithDto.getUsername());
    Assertions.assertEquals(data.password(), userWithDto.getPassword());
    Assertions.assertEquals(data.firstName(), userWithDto.getFirstName());
    Assertions.assertEquals(data.lastName(), userWithDto.getLastName());
    Assertions.assertEquals(data.email(), userWithDto.getEmail());
    Assertions.assertEquals(data.birthData(), userWithDto.getBirthData());
    Assertions.assertEquals(data.balance(), userWithDto.getBalance());
  }
}
