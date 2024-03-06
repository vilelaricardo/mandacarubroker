package com.mandacarubroker.domain.user;

import com.mandacarubroker.dtos.RequestUserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {

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
