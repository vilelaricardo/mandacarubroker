package com.mandacarubroker.domain.treasury;

import java.sql.Timestamp;

/**
 * Data encapsulation to register a treasure.
 *
 * @param name The treasury name
 * @param amount The amount
 * @param type The treasury type
 * @param maturityDate The final data
 * @param interestRate The interest rate
 * @param price The price
 *
 * */
public record RegisterDataTransferObject(
    String name,
    Integer amount,
    String type,
    Timestamp maturityDate,
    Float interestRate,
    Float price
) {}
