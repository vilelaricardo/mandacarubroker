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


/**
* The Stock class.
* */
@Table(name = "stock")
@Entity(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String symbol;
  private String companyName;
  private double price;
  private Integer amount;

  /**
   * The method set the variables.
   *
   * @param requestStock The stock data
   *
   **/
  public Stock(RequestStockDataTransferObject requestStock) {
    this.symbol = requestStock.symbol();
    this.companyName = requestStock.companyName();
    this.price = requestStock.price();
    this.amount = requestStock.amount();
  }

  /**
   * The method change the stock price.
   *
   * @param amount stock price
   * @param increase if increase the stock
   *
   **/
  public double changePrice(double amount, boolean increase) {
    if (amount < 0) {
      throw new IllegalArgumentException("The amount value cannot be negative.");
    }

    if (increase) {
      return increasePrice(amount);
    } else {
      if (amount < this.price) {
        return decreasePrice(amount);
      } else {
        return 0.00;
      }
    }
  }

  private double increasePrice(double amount) {
    return this.price + amount;
  }

  private double decreasePrice(double amount) {
    return this.price - amount;
  }
}