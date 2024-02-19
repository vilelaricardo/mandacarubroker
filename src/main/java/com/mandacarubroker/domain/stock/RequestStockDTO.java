package com.mandacarubroker.domain.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record RequestStockDTO(
        @Pattern(regexp = "[A-Za-z]{3}\\d", message = "Symbol must be 3 letters followed by 1 number")
        String symbol,
        @NotBlank(message = "Company name cannot be blank")
        String companyName,
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        double price
) {
}
