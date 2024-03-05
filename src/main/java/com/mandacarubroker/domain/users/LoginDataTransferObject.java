package com.mandacarubroker.domain.users;

/**
 * Data encapsulation to user login.
 *
 * @param username user login
 * @param password user password
 *
 * */
public record LoginDataTransferObject(String username, String password) {
}
