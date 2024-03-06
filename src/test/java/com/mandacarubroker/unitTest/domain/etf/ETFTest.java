package com.mandacarubroker.unitTest.domain.etf;

import com.mandacarubroker.domain.etf.ETF;
import com.mandacarubroker.domain.etf.RegisterDataTransferObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ETFTest {

  private ETF etf;

  @BeforeEach
  public void setUp() {
    etf = new ETF();
  }

  @Test
  void testSetName() {
    etf.setName("Test ETF");
    Assertions.assertEquals("Test ETF", etf.getName());
  }

  @Test
  void testSetSymbol() {
    etf.setSymbol("TEST");
    Assertions.assertEquals("TEST", etf.getSymbol());
  }

  @Test
  void testSetCompanyName() {
    etf.setCompanyName("Test Company");
    Assertions.assertEquals("Test Company", etf.getCompanyName());
  }

  @Test
  void testSetAmount() {
    etf.setAmount(100);
    Assertions.assertEquals(100, etf.getAmount());
  }

  @Test
  void testSetPrice() {
    etf.setPrice(50.5f);
    Assertions.assertEquals(50.5f, etf.getPrice());
  }

  @Test
  void testConstructorWithDataTransferObject() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
      "Test ETF",
      "TEST",
      "Test Company",
      "funds",
      2,
      30.03f
    );

    ETF etfWithDto = new ETF(data);

    Assertions.assertEquals(data.name(), etfWithDto.getName());
    Assertions.assertEquals(data.companyName(), etfWithDto.getCompanyName());
    Assertions.assertEquals(data.symbol(), etfWithDto.getSymbol());
    Assertions.assertEquals(data.amount(), etfWithDto.getAmount());
    Assertions.assertEquals(data.price(), etfWithDto.getPrice());
  }
}
