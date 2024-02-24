package com.mandacarubroker.domain.user;

import java.time.LocalDate;

public record RequestUserDTO(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate,
        Double balance
) { }
