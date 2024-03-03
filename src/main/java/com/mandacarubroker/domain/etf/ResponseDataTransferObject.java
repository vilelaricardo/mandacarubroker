package com.mandacarubroker.domain.etf;

/**
 * Data encapsulation to response.
 *
 * @param success If response were successful
 * @param message The response message
 * @param data The response data
 *
 * */
public record ResponseDataTransferObject(
    Boolean success,
    String message,
    Object data
) {}
