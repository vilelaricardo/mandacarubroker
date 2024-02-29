package com.mandacarubroker.domain.user;

import com.mandacarubroker.validations.BirthDateValidate;
import com.mandacarubroker.validations.UniqueValidate;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RequestUserDTO(
        @NotBlank @Size(min = 4, max = 20, message = "Username size must be between 4 and 20 characters.") @Pattern(regexp = "[A-Za-z]{3}\\d", message = "Username must have letters and numbers and cannot have symbols.") @UniqueValidate
        String username,
        @NotBlank @Size(min = 8, message = "Password cannot be less of 8 characters long") @Pattern(regexp = "^(?=.[a-zA-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must have letters, numbers and special characters")
        String password,
        @NotBlank @Email @UniqueValidate
        String email,
        @NotBlank @Pattern(regexp = "^[a-zA-Z]+$")
        String firstName,
        @NotBlank @Pattern(regexp = "^[a-zA-Z]+$")
        String lastName,
        @BirthDateValidate
        LocalDate birthDate,
        @DecimalMin(value = "0", inclusive = false)
        Double balance
) { }
