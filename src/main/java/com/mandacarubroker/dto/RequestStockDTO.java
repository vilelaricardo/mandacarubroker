package com.mandacarubroker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para requisição de ações.
 * DTO: "Data Transfer Object" (Objeto de Transferência de Dados)
 *
 * @author Equipe de desenvolvimento mandacaru
 */
public record RequestStockDTO(

        // Símbolo da ação (3 letras + 1 número). Exemplo: "PET4"
        @Pattern(regexp = "[A-Za-z]{3}[0-9]{1}", message = "Symbol must be 3 letters followed by 1 number (O símbolo deve ter 3 letras seguidas por 1 número).")
        String symbol,

        // Nome da empresa de onde será creditada ou debitada as ações.
        @NotBlank(message = "Company name cannot be blank (O nome da empresa não pode ser vazio).")
        String companyName,

        // Preço da ação.
        @NotNull(message = "O preço não pode ser nulo")
        double price
) {
}