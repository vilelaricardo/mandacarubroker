package com.mandacarubroker.domain.realestatesfunds;

/**
 * Data encapsulation to register a treasure.
 *
 * @param name The treasury name
 * @param amount The amount
 * @param type The treasury type
 * @param symbol The symbol
 * @param companyName The company name
 * @param price The price
 *
 * */
public record RegisterDataTransferObject(
    String name,
    String symbol,
    String companyName,
    String type,
    Integer amount,
    Float price
) {}
