package com.mandacarubroker.domain.treasury;

import jakarta.persistence.*;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Treasury class.
 * */
@Table(name = "treasury")
@Entity(name = "treasury")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Treasury {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
  private Integer amount;
  private String type;
  @Column(name = "maturity_date")
  private Timestamp maturityDate;
  @Column(name = "interest_rate")
  private Float interestRate;
  private Float price;

  /**
   * The method set the variables.
   *
   * @param data The treasury data
   *
   **/
  public Treasury(RegisterDataTransferObject data) {
    this.name = data.name();
    this.amount = data.amount();
    this.type = data.type();
    this.maturityDate = data.maturityDate();
    this.interestRate = data.interestRate();
    this.price = data.price();
  }

}
