package com.mandacarubroker.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String symbol;
    private String companyName;
    private double price;

    public Stock(final RequestStockDTO requestStockDTO) {
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = requestStockDTO.price();
    }

    public void setPrice(final double newPrice) {
        if (price <= 0) {
            throw new IllegalArgumentException("Stock price cannot be negative or zero");
        }

        this.price = newPrice;
    }

    public double changePrice(final double amount, final boolean increase) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (increase) {
            return increasePrice(amount);
        }

        return decreasePrice(amount);
    }

    public double increasePrice(final double amount) {
        return this.price + amount;
    }

    public double decreasePrice(final double amount) {
        return this.price - amount;
    }
}
