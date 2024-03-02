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
 * @param first_name user first name
 * @param last_name user last name
 * @param birth_data user birth data
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
    String first_name,
    @NotBlank(message = "The last name cannot be blank")
    String last_name,
    @NotNull(message = "The birth data cannot be blank")
    @Past
    Timestamp birth_data,
    Double balance
) {}
