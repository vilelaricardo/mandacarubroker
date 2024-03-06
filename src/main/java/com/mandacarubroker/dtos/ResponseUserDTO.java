package com.mandacarubroker.dtos;

import com.mandacarubroker.domain.user.User;
import java.time.LocalDate;

public record ResponseUserDTO (
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate,
        Double balance
){
    public ResponseUserDTO(User user){
        this(user.getId(), user.getUsername(),user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getBalance());
    }
}
