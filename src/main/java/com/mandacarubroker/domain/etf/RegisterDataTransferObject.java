package com.mandacarubroker.domain.etf;

/**
 * Data encapsulation to register a ETF.
 *
 * @param name The treasury name
 * @param amount The amount
 * @param type The treasury type
 * @param symbol The symbol
 * @param company_name The company name
 * @param price The price
 *
 * */
public record RegisterDataTransferObject(
    String name,
    String symbol,
    String company_name,
    String type,
    Integer amount,
    Float price
) {}