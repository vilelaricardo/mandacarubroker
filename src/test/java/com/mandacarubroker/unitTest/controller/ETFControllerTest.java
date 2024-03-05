package com.mandacarubroker.unitTest.controller;

import com.mandacarubroker.controller.ETFController;
import com.mandacarubroker.domain.etf.ETF;
import com.mandacarubroker.domain.etf.GetResponseDataTransferObject;
import com.mandacarubroker.domain.etf.RegisterDataTransferObject;
import com.mandacarubroker.domain.etf.ResponseDataTransferObject;
import com.mandacarubroker.repository.ETFRepository;
import com.mandacarubroker.service.ETFService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class ETFControllerTest {

  @Mock
  private ETFService etfService;

  @Mock
  private ETFRepository etfRepository;

  @InjectMocks
  private ETFController etfController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAll() {
    List<ETF> etfList = new ArrayList<>();
    etfList.add(new ETF());
    etfList.add(new ETF());
    etfList.add(new ETF());

    Mockito.when(etfService.getAll()).thenReturn(etfList);

    ResponseEntity<GetResponseDataTransferObject> response = etfController.getAll();

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(etfList, response.getBody().data());
  }

  @Test
  void testGet() {
    String id = "123456";
    Optional<ETF> etf = Optional.of(new ETF(
        "TEST",
        "TEST3",
        "Test s.a",
        "TEST",
        10,
        20.0f
    ));

    Mockito.when(etfService.get(id)).thenReturn(etf);

    ResponseEntity<ResponseDataTransferObject> response = etfController.get(id);


    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertNull(response.getBody().message());
    Assertions.assertEquals(etf.get(), response.getBody().data());
  }

  @Test
  void testGet_NotFound() {
    String id = "123456";
    Optional<ETF> etf = Optional.empty();

    Mockito.when(etfService.get(id)).thenReturn(etf);

    ResponseEntity<ResponseDataTransferObject> response = etfController.get(id);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("register not found", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testRegister_NewETF() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "TEST",
        "TEST3",
        "Test s.a",
        "TEST",
        10,
        20.0f
        );

    Mockito.when(etfRepository.getBySymbol(anyString())).thenReturn(null);
    Mockito.when(etfService.create(any(RegisterDataTransferObject.class))).thenReturn(new ETF());

    ResponseEntity<ResponseDataTransferObject> response = etfController.register(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertNull(response.getBody().message());
    Assertions.assertNotNull(response.getBody().data());
  }

  @Test
  void testRegister_ExistingETF() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "TEST",
        "TEST3",
        "Test s.a",
        "TEST",
        10,
        20.0f
    );

    Mockito.when(etfRepository.getBySymbol(anyString())).thenReturn(new ETF());

    ResponseEntity<ResponseDataTransferObject> response = etfController.register(data);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("Already registered", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testUpdate() {
    String id = "123456";
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "TEST",
        "TEST3",
        "Test s.a",
        "TEST",
        10,
        20.0f
    );

    ETF updatedETF = new ETF("TEST A",
        "TEST4",
        "Test s.a",
        "TEST",
        23,
        22.0f
    );
    Mockito.when(etfService.update(anyString(), any(RegisterDataTransferObject.class))).thenReturn(Optional.of(updatedETF));

    ResponseEntity<ResponseDataTransferObject> response = etfController.update(id, data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertNull(response.getBody().message());
    Assertions.assertEquals(updatedETF, response.getBody().data());
  }

  @Test
  void testUpdate_Error() {
    String id = "123456";
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "TEST",
        "TEST3",
        "Test s.a",
        "TEST",
        10,
        20.0f
    );

    Mockito.when(etfService.update(anyString(), any(RegisterDataTransferObject.class))).thenReturn(Optional.empty());

    ResponseEntity<ResponseDataTransferObject> response = etfController.update(id, data);

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("There was an error", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testDelete() {
    String id = "123456";

    ResponseEntity<ResponseDataTransferObject> response = etfController.delete(id);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals("successfully deleted", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }
}

