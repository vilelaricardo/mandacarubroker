package com.mandacarubroker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record RequestUserDTO(

        @NotBlank(message = "Campo 'username' não pode estar em branco")
        @Pattern(regexp = "^(?!\\s*$).+", message = "Campo 'username' não deve conter espaços")
        String username,

        @NotBlank(message = "Campo 'password' não pode estar em branco")
        String password,

        @NotBlank(message = "Campo 'email' não pode estar em branco")
        @Email(message = "Endereço de email inválido")
        String email,

        @NotBlank(message = "Campo 'first name' não pode estar em branco")
        String firstName,

        @NotBlank(message = "Campo 'last name' não pode estar em branco")
        String lastName,

        @NotNull(message = "Campo 'birth date' não pode ser nulo")
        @Past(message = "Data de nascimento deve ser uma data passada")
        LocalDate birthDate
) {}