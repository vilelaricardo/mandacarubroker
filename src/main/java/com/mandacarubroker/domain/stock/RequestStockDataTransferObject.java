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
  String symbol,
  @NotBlank(message = "Company name cannot be blank")
  String companyName,
  @NotNull(message = "Price cannot be null")
  double price
) {
}
