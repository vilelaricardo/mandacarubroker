package com.mandacarubroker.domain.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {
    @Test
    void testUserConstructor() {
        RequestUserDTO dto = new RequestUserDTO(
                "geovana",
                "senha@123",
                "geovana21072003@gmail.com",
                "Geovana",
                "Souza",
                LocalDate.of(2003, 7, 21),
                100000.0
        );

        User user = new User(dto);

        assertEquals("geovana", user.getUsername());
        assertEquals("senha@123", user.getPassword());
        assertEquals("geovana21072003@gmail.com", user.getEmail());
        assertEquals("Geovana", user.getFirstName());
        assertEquals("Souza", user.getLastName());
        assertEquals(LocalDate.of(2003, 7, 21), user.getBirthDate());
        assertEquals(100000.0, user.getBalance());
    }

    @Test
    void testUserEqualsAndHashCode() {
        User user1 = new User();
        user1.setId("1");

        User user2 = new User();
        user2.setId("1");

        User user3 = new User();
        user3.setId("2");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
}
