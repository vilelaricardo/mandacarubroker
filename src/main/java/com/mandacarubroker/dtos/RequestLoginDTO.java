package com.mandacarubroker.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestLoginDTO (
        @NotBlank @Size(min = 4, max = 20, message = "Username size must be between 4 and 20 characters.")
        String username,
        @NotBlank @Size(min = 8, message = "Password cannot be less of 8 characters long")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must have letters, numbers and special characters")
        String password
){}
