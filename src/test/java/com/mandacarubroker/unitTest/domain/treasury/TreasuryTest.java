package com.mandacarubroker.unitTest.domain.treasury;

import com.mandacarubroker.domain.treasury.RegisterDataTransferObject;
import com.mandacarubroker.domain.treasury.Treasury;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;

class TreasuryTest {

  private Treasury treasury;

  @BeforeEach
  public void setUp() {
    treasury = new Treasury();
  }

  @Test
  void testSetName() {
    treasury.setName("Test Treasury");
    Assertions.assertEquals("Test Treasury", treasury.getName());
  }

  @Test
  void testSetAmount() {
    treasury.setAmount(100);
    Assertions.assertEquals(100, treasury.getAmount());
  }

  @Test
  void testSetType() {
    treasury.setType("Type A");
    Assertions.assertEquals("Type A", treasury.getType());
  }

  @Test
  void testSetMaturityDate() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    treasury.setMaturityDate(timestamp);
    Assertions.assertEquals(timestamp, treasury.getMaturityDate());
  }

  @Test
  void testSetInterestRate() {
    treasury.setInterestRate(0.05f);
    Assertions.assertEquals(0.05f, treasury.getInterestRate());
  }

  @Test
  void testSetPrice() {
    treasury.setPrice(1000.0f);
    Assertions.assertEquals(1000.0f, treasury.getPrice());
  }

  @Test
  void testConstructorWithDataTransferObject() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Treasury",
        100,
        "Type A",
        new Timestamp(System.currentTimeMillis()),
        0.05f,
        1000.0f
    );

    Treasury treasuryWithDto = new Treasury(data);

    Assertions.assertEquals(data.name(), treasuryWithDto.getName());
    Assertions.assertEquals(data.amount(), treasuryWithDto.getAmount());
    Assertions.assertEquals(data.type(), treasuryWithDto.getType());
    Assertions.assertEquals(data.maturityDate(), treasuryWithDto.getMaturityDate());
    Assertions.assertEquals(data.interestRate(), treasuryWithDto.getInterestRate());
    Assertions.assertEquals(data.price(), treasuryWithDto.getPrice());
  }
}
