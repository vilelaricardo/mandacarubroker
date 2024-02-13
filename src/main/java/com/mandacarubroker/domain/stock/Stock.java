package com.mandacarubroker.domain.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

class PriceChangeException extends RuntimeException {
    public PriceChangeException(String message) {
        super(message);
    }
}


@Table(name ="stock")
@Entity(name="stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String symbol;
    private String companyName;
    private double price;

    public Stock(RequestStockDTO requestStockDTO){
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = changePrice(requestStockDTO.price());
    }

    public double changePrice(double amount) {
        if (!(amount < 0) && amount != 0){
            return this.price = amount;
        }
        throw new PriceChangeException("Invalid price change request. Amount must be positive and non-zero.");
    }

}