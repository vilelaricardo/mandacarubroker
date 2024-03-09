package com.mandacarubroker.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record RequestStockDTO(
        @NotBlank @Pattern(regexp = "[A-Za-z]{3}\\d", message = "Symbol must be 3 letters followed by 1 number")
        String symbol,
        @NotBlank(message = "Company name cannot be blank")
        String companyName,
        @Positive(message = "Price cannot be negative")
        @NotNull(message = "Price cannot be null")
        double price
){
}

