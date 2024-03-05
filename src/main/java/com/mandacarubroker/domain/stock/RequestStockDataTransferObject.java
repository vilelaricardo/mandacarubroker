package com.mandacarubroker.domain.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
* Data encapsulation to create an stock.
*
* @param symbol the stock code
* @param companyName the company name of the stock
* @param price the stock price
*
* */
public record RequestStockDataTransferObject(
  @Pattern(
    regexp = "[A-Za-z]{4}\\d",
    message = "Symbol must be 4 letters followed by 1 number"
  )
  @NotBlank(message = "The symbol cannot be blank")
  String symbol,
  @NotBlank(message = "The company name cannot be blank")
  String companyName,
  @NotNull(message = "The price cannot be null")
  Double price,
  Integer amount
) {}
