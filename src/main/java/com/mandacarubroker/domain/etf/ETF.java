package com.mandacarubroker.domain.etf;

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
 * The ETF data class.
 * */
@Table(name = "etf")
@Entity(name = "etf")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ETF {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String symbol;
  private String company_name;
  private Integer amount;
  private Float price;

  /**
   * The method set the variables.
   *
   * @param data The ETF data
   *
   **/
  public ETF(RegisterDataTransferObject data) {
    this.name = data.name();
    this.company_name = data.company_name();
    this.symbol = data.symbol();
    this.amount = data.amount();
    this.price = data.price();
  }
}
