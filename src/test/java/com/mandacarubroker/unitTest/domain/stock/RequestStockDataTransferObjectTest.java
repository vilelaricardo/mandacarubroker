package com.mandacarubroker.unitTest.domain.stock;

import static org.junit.jupiter.api.Assertions.*;

import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RequestStockDataTransferObjectTest {
  private static Validator validator;

  @BeforeAll
  public static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidStockData() {
    String symbol = "ABCD1";
    String companyName = "Company Name";
    Double price = 10.50;
    Integer amount = 1;

    RequestStockDataTransferObject stockDTO = new RequestStockDataTransferObject(symbol, companyName, price, amount);
    var violations = validator.validate(stockDTO);

    assertTrue(violations.isEmpty());
  }

  @Test
  void testInvalidSymbol() {
    String symbol = "AB1";
    String companyName = "Company Name";
    Double price = 10.5;
    Integer amount = 1;

    RequestStockDataTransferObject stockDTO = new RequestStockDataTransferObject(symbol, companyName, price, amount);
    var violations = validator.validate(stockDTO);

    assertEquals(1, violations.size());
  }

  @Test
  void testBlankCompanyName() {
    String symbol = "ABCD3";
    String companyName = "";
    Double price = 10.5;
    Integer amount = 1;

    RequestStockDataTransferObject stockDTO = new RequestStockDataTransferObject(symbol, companyName, price, amount);
    var violations = validator.validate(stockDTO);

    assertEquals(1, violations.size());
  }

  @Test
  void testNullPrice() {
    String symbol = "ABCD3";
    String companyName = "Company Name";
    Double price = null;
    Integer amount = 1;

    RequestStockDataTransferObject stockDTO = new RequestStockDataTransferObject(symbol, companyName, price, amount);
    var violations = validator.validate(stockDTO);

    assertEquals(1, violations.size());
  }

  @Test
  void testAllNull() {
    String symbol = null;
    String companyName = null;
    Double price = null;
    Integer amount = 1;

    RequestStockDataTransferObject stockDTO = new RequestStockDataTransferObject(symbol, companyName, price, amount);
    var violations = validator.validate(stockDTO);

    assertEquals(3, violations.size());
  }
}
