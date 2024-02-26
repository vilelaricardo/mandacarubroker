package com.mandacarubroker.domain.authuser;

/**
 * Data encapsulation to create an auth user.
 *
 * @param username The auth user login
 * @param password The auth user password
 * @param role The auth user role
 *
 * */
public record RegisterDataTransferObject(String username, String password, AuthUserRole role) {
}
