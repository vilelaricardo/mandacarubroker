package com.mandacarubroker.domain.user;

import com.mandacarubroker.dtos.RequestUserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestUserDTOTest {
     @Test
     void testValidRequestUserDTO() {
         RequestUserDTO validDTO = new RequestUserDTO(
                 "geovana123",
                 "geovana@123",
                 "geovana21072003@gmail.com",
                 "Geovana",
                 "Souza",
                 LocalDate.of(2003, 7, 21),
                 100.0
         );

         assertEquals("geovana123", validDTO.username());
         assertEquals("geovana@123", validDTO.password());
         assertEquals("geovana21072003@gmail.com", validDTO.email());
         assertEquals("Geovana", validDTO.firstName());
         assertEquals("Souza", validDTO.lastName());
         assertEquals(LocalDate.of(2003, 7, 21), validDTO.birthDate());
         assertEquals(100.0, validDTO.balance());
     }

}
