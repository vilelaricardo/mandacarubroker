package com.mandacarubroker.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

    /**
     * Identificador único do estoque.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Símbolo do estoque.
     */
    private String symbol;

    /**
     * Nome da empresa do estoque.
     */
    private String companyName;

    /**
     * Preço do estoque.
     */
    private double price;

    /**
     * Construtor que inicializa os campos da classe com base em um objeto RequestStockDTO.
     *
     * @param requestStockDTO Objeto contendo os dados do estoque.
     */
    public Stock(final RequestStockDTO requestStockDTO) {
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = changePrice(requestStockDTO.price(), true);
    }

    /**
     * Método que altera o preço do estoque com base no valor e na direção da mudança.
     *
     * @param amount   Valor da mudança de preço.
     * @param increase Se verdadeiro, aumenta o preço; se falso, diminui o preço.
     * @return O novo preço do estoque após a mudança.
     */
    public double changePrice(final double amount, final boolean increase) {
        if (increase) {
            if (amount < this.price) {
                return increasePrice(amount);
            } else {
                return decreasePrice(amount);
            }
        } else {
            if (amount > this.price) {
                return increasePrice(amount);
            } else {
                return this.decreasePrice(amount);
            }
        }
    }

    /**
     * Método para aumentar o preço do estoque em uma quantidade específica.
     *
     * @param amount Quantidade a ser adicionada ao preço atual.
     * @return O novo preço do estoque após o aumento.
     */
    public double increasePrice(final double amount) {
        return this.price + amount;
    }

    /**
     * Método para diminuir o preço do estoque em uma quantidade específica.
     *
     * @param amount Quantidade a ser subtraída do preço atual.
     * @return O novo preço do estoque após a diminuição.
     */
    public double decreasePrice(final double amount) {
        return this.price - amount;
    }
}
