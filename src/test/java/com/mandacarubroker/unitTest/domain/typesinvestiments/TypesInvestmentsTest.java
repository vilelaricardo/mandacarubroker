package com.mandacarubroker.unitTest.domain.typesinvestiments;

import com.mandacarubroker.domain.typesinvestiments.RegisterTypeInvestment;
import com.mandacarubroker.domain.typesinvestiments.TypesInvestments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypesInvestmentsTest {

  private TypesInvestments typesInvestments;

  @BeforeEach
  public void setUp() {
    typesInvestments = new TypesInvestments();
  }

  @Test
  void testSetName() {
    typesInvestments.setName("Test Investment Type");
    Assertions.assertEquals("Test Investment Type", typesInvestments.getName());
  }

  @Test
  void testConstructorWithRegisterTypeInvestment() {
    RegisterTypeInvestment data = new RegisterTypeInvestment("Test Investment Type");

    TypesInvestments typesInvestmentsWithDto = new TypesInvestments(data);

    Assertions.assertEquals(data.name(), typesInvestmentsWithDto.getName());
  }
}
