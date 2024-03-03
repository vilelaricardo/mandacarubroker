package com.mandacarubroker.domain.treasury;

import java.util.List;


/**
 * Data encapsulation to response.
 *
 * @param success If response were successful
 * @param message The response message
 * @param data The response data
 *
 * */
public record GetResponseDataTransferObject(
    Boolean success,
    String message,
    List<Treasury> data
) {}
