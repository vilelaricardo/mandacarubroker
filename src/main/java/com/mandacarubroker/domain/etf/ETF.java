package com.mandacarubroker.domain.etf;

import jakarta.persistence.*;
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
  @Column(name = "company_name")
  private String companyName;
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
    this.companyName = data.companyName();
    this.symbol = data.symbol();
    this.amount = data.amount();
    this.price = data.price();
  }
}
