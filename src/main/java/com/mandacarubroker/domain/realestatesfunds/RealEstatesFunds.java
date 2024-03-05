package com.mandacarubroker.domain.realestatesfunds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Real Estates Funds data class.
 * */
@Table(name = "real_estate_investment_funds")
@Entity(name = "real_estate_investment_funds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RealEstatesFunds {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private String symbol;
  @Column(name = "company_name")
  private String companyName;
  private String type;
  private Integer amount;
  private Float price;

  /**
   * The method set the variables.
   *
   * @param data The Real Estates Funds data
   *
   **/
  public RealEstatesFunds(RegisterDataTransferObject data) {
    this.name = data.name();
    this.companyName = data.companyName();
    this.symbol = data.symbol();
    this.type = data.type();
    this.amount = data.amount();
    this.price = data.price();
  }
}
