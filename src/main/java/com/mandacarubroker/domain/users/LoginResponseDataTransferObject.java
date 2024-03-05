package com.mandacarubroker.domain.users;

/**
 * Data encapsulation to login response.
 *
 * @param success success environment
 * @param message message to the user
 * @param data response data
 *
 * */
public record LoginResponseDataTransferObject(Boolean success, String message, Object data) {
}
