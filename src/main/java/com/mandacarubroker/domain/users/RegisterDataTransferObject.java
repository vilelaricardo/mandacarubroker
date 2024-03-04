package com.mandacarubroker.domain.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.sql.Timestamp;

/**
 * Data encapsulation to create an user.
 *
 * @param username user login
 * @param password user password
 * @param email user email
 * @param firstName user first name
 * @param lastName user last name
 * @param birthData user birth data
 * @param balance user balance
 *
 * */
public record RegisterDataTransferObject(
    @NotBlank(message = "The username cannot be blank")
    String username,
    @NotBlank(message = "The password cannot be blank")
    String password,
    @NotBlank(message = "The email cannot be blank")
    String email,
    @NotBlank(message = "The first name cannot be blank")
    String firstName,
    @NotBlank(message = "The last name cannot be blank")
    String lastName,
    @NotNull(message = "The birth data cannot be blank")
    @Past
    Timestamp birthData,
    Double balance
) {}
