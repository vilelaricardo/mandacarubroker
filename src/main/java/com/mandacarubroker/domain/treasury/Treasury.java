package com.mandacarubroker.domain.treasury;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  private Timestamp maturity_date;
  private Float interest_rate;
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
    this.maturity_date = data.maturity_date();
    this.interest_rate = data.interest_rate();
    this.price = data.price();
  }

}
