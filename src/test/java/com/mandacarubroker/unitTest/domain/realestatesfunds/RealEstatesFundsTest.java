package com.mandacarubroker.unitTest.domain.realestatesfunds;

import com.mandacarubroker.domain.realestatesfunds.RealEstatesFunds;
import com.mandacarubroker.domain.realestatesfunds.RegisterDataTransferObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RealEstatesFundsTest {

  private RealEstatesFunds realEstatesFunds;

  @BeforeEach
  public void setUp() {
    realEstatesFunds = new RealEstatesFunds();
  }

  @Test
  void testSetName() {
    realEstatesFunds.setName("Test Real Estate Fund");
    Assertions.assertEquals("Test Real Estate Fund", realEstatesFunds.getName());
  }

  @Test
  void testSetSymbol() {
    realEstatesFunds.setSymbol("TEST");
    Assertions.assertEquals("TEST", realEstatesFunds.getSymbol());
  }

  @Test
  void testSetCompanyName() {
    realEstatesFunds.setCompanyName("Test Company");
    Assertions.assertEquals("Test Company", realEstatesFunds.getCompanyName());
  }

  @Test
  void testSetType() {
    realEstatesFunds.setType("Type A");
    Assertions.assertEquals("Type A", realEstatesFunds.getType());
  }

  @Test
  void testSetAmount() {
    realEstatesFunds.setAmount(100);
    Assertions.assertEquals(100, realEstatesFunds.getAmount());
  }

  @Test
  void testSetPrice() {
    realEstatesFunds.setPrice(50.5f);
    Assertions.assertEquals(50.5f, realEstatesFunds.getPrice());
  }

  @Test
  void testConstructorWithDataTransferObject() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Real Estate Fund",
        "TEST",
        "Test Company",
        "Type A",
        100,
        50.5f
    );

    RealEstatesFunds realEstatesFundsWithDto = new RealEstatesFunds(data);

    Assertions.assertEquals(data.name(), realEstatesFundsWithDto.getName());
    Assertions.assertEquals(data.companyName(), realEstatesFundsWithDto.getCompanyName());
    Assertions.assertEquals(data.symbol(), realEstatesFundsWithDto.getSymbol());
    Assertions.assertEquals(data.type(), realEstatesFundsWithDto.getType());
    Assertions.assertEquals(data.amount(), realEstatesFundsWithDto.getAmount());
    Assertions.assertEquals(data.price(), realEstatesFundsWithDto.getPrice());
  }
}
