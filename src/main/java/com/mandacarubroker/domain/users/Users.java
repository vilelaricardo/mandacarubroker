package com.mandacarubroker.domain.users;

import jakarta.persistence.Column;
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
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "birth_data")
  private Timestamp birthData;
  private Double balance;

  /**
   * User constructor.
   **/
  public Users(RegisterDataTransferObject data, String password) {
    this.username = data.username();
    this.password = password;
    this.firstName = data.firstName();
    this.lastName = data.lastName();
    this.email = data.email();
    this.birthData = data.birthData();
    this.balance = data.balance();
  }
}