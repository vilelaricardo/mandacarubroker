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

/**
 * Entity representing a stock.
 */
@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

    /**
     * Unique identifier of the stock.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * Symbol of the stock.
     */
    private String symbol;

    /**
     * Company name associated with the stock.
     */
    private String companyName;

    /**
     * Price of the stock.
     */
    private double price;

    /**
     * Constructor for creating a Stock object from a RequestStockDTO.
     *
     * @param requestStockDTO The RequestStockDTO object
     */
    public Stock(final RequestStockDTO requestStockDTO) {
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        double requestedPrice = requestStockDTO.price();
        if (requestedPrice <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = requestedPrice;
    }

    /**
     * Sets the price of the stock to the specified amount.
     *
     * @param newPrice The new price for the stock
     * @throws IllegalArgumentException if the new price is negative or zero
     */
    public void setPrice(final double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = newPrice;
    }
}
