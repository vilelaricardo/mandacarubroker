package com.mandacarubroker.dtos;

import com.mandacarubroker.domain.user.User;
import java.time.LocalDate;

public record RequestUserDTO(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate,
        Double balance
) {
    public RequestUserDTO(User user){
        this(user.getUsername(),user.getPassword(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getBirthDate(),user.getBalance());
    }
}
