package com.mandacarubroker.domain.stock;

import com.mandacarubroker.dto.RequestStockDTO;
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
 * Classe "Stock", para criar e modificar dados de ações.
 *
 * @author Equipe de desenvolvimento mandacaru
 */
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

  // ---------- GETTERS ---------- //
  public String getId() {
    return id;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getCompanyName() {
    return companyName;
  }

  public double getPrice() {
    return price;
  }

  // ---------- SETTERS ---------- //
  public void setId(String id) {
    this.id = id;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Construtor que cria uma instância de Stock a partir de um RequestStockDTO.
   *
   * @param requestStockDTO o DTO contendo os dados da ação.
   */
  public Stock(RequestStockDTO requestStockDTO) {
    this.symbol = requestStockDTO.symbol();
    this.companyName = requestStockDTO.companyName();
    this.price = changePrice(requestStockDTO.price(), true);
  }

  /**
   * Modifica o preço da ação de acordo com o valor e a direção (aumento ou diminuição).
   *
   * @param amount o valor da alteração de preço.
   * @param increase true para aumentar o preço, false para diminuir.
   * @return o novo valor do preço.
   */
  public double changePrice(double amount, boolean increase) {
    if (increase) {
      return this.price + amount;
    } else {
      if (amount > this.price) {
        return this.price;
      } else {
        return this.price - amount;
      }
    }
  }
}