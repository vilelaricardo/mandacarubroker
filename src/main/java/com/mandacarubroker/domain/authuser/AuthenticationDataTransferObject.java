package com.mandacarubroker.domain.authuser;

/**
 * Data encapsulation to create an authentication data.
 *
 * @param username The auth user login
 * @param password The auth user password
 *
 * */
public record AuthenticationDataTransferObject(String username, String password) {
}
