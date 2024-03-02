package com.mandacarubroker.domain.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;



/**
 * The Users class.`
* */
@Table(name = "users")
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String username;
  @Getter
  private String password;
  private String email;
  private String first_name;
  private String last_name;
  private Timestamp birth_data;
  private Double balance;

  /**
   * User constructor.
   **/
  public Users(RegisterDataTransferObject data, String password) {
    this.username = data.username();
    this.password = password;
    this.first_name = data.first_name();
    this.last_name = data.last_name();
    this.email = data.email();
    this.birth_data = data.birth_data();
    this.balance = data.balance();
  }
}