package com.mandacarubroker.domain.stock;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

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

     O identificador único para a ação.
     Este campo é anotado com {@code @Id} e {@code @GeneratedValue} para indicar que
     representa a chave primária da entidade de ação. A estratégia {@code GenerationType.UUID}
     é usada para gerar um UUID único como identificador.

     */
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     *
     * O símbolo único associado à ação.
     */
    private String symbol;

    /**
     * O nome da empresa associada à ação.
     */
    private String companyName;
    /**
     * O preço atual da ação.
     */
    private double price;

    /**
     * Constrói um novo objeto Stock com base no RequestStockDTO fornecido.
     *
     * Este construtor inicializa um novo objeto Stock. Ele extrai o símbolo,
     * o nome da empresa e o preço do RequestStockDTO e define os atributos correspondentes do Stock.
     *
     * @param requestStockDTO O RequestStockDTO contendo os dados para inicializar o Stock.
     */
    public Stock(final RequestStockDTO requestStockDTO) {
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = requestStockDTO.price();
    }


}