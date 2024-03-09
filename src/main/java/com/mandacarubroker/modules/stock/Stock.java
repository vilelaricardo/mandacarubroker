package com.mandacarubroker.modules.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade "Stock", com parâmetros dos dados de ações.
 *
 * @author Equipe de desenvolvimento mandacaru
 */
@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "stockId")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String stockId;

    // Símbolo da ação (3 letras + 1 número). Exemplo: "PET4"
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[A-Za-z]{3}[0-9]{1}", message = "O símbolo deve ter 3 letras seguidas por 1 número")
    private String symbol;

    // Nome da empresa com ações para serem comercializadas.
    @Column(nullable = false)
    @NotBlank(message = "O nome da empresa não pode ser vazio")
    private String companyName;

    // Preço da ação.
    @Column(nullable = false)
    @NotNull(message = "O preço não pode ser nulo")
    private float price;

    // Quantidade de ações.
    @Column(nullable = false)
    @NotNull(message = "A quantidade de ações não pode ser nula")
    private int quantity;

}