package com.mandacarubroker.domain.typesinvestiments;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "types_investments")
@Entity(name = "types_investments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TypesInvestments {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;


  public TypesInvestments(RegisterTypeInvestment data) {
    this.name = data.name();
  }
}
