package com.mandacarubroker.unitTest.controller;

import com.mandacarubroker.controller.RealEstatesFundsController;
import com.mandacarubroker.domain.realestatesfunds.GetResponseDataTransferObject;
import com.mandacarubroker.domain.realestatesfunds.RealEstatesFunds;
import com.mandacarubroker.domain.realestatesfunds.RegisterDataTransferObject;
import com.mandacarubroker.domain.realestatesfunds.ResponseDataTransferObject;
import com.mandacarubroker.repository.RealEstatesFundsRepository;
import com.mandacarubroker.service.RealEstatesFundsService;
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

class RealEstatesFundsControllerTest {

  @Mock
  private RealEstatesFundsService service;

  @Mock
  private RealEstatesFundsRepository repository;

  @InjectMocks
  private RealEstatesFundsController controller;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAll() {
    List<RealEstatesFunds> fundsList = new ArrayList<>();
    fundsList.add(new RealEstatesFunds());
    fundsList.add(new RealEstatesFunds());
    fundsList.add(new RealEstatesFunds());

    Mockito.when(service.getAll()).thenReturn(fundsList);

    ResponseEntity<GetResponseDataTransferObject> response = controller.getAll();

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(fundsList, response.getBody().data());
  }

  @Test
  void testGet() {
    String id = "123456";
    Optional<RealEstatesFunds> funds = Optional.of(new RealEstatesFunds());

    Mockito.when(service.get(id)).thenReturn(funds);

    ResponseEntity<ResponseDataTransferObject> response = controller.get(id);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertNull(response.getBody().message());
    Assertions.assertEquals(funds.get(), response.getBody().data());
  }

  @Test
  void testGet_NotFound() {
    String id = "123456";
    Optional<RealEstatesFunds> funds = Optional.empty();

    Mockito.when(service.get(id)).thenReturn(funds);

    ResponseEntity<ResponseDataTransferObject> response = controller.get(id);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("register not found", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testRegister() {
    RegisterDataTransferObject data = new RegisterDataTransferObject("Test Real Estate Fund",
        "TEST",
        "Test Company",
        "Type A",
        100,
        50.5f);
    RealEstatesFunds createdFunds = new RealEstatesFunds(data);

    Mockito.when(repository.getBySymbol(anyString())).thenReturn(null);
    Mockito.when(service.create(any(RegisterDataTransferObject.class))).thenReturn(createdFunds);

    ResponseEntity<ResponseDataTransferObject> response = controller.register(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals("successfully registered", response.getBody().message());
    Assertions.assertEquals(createdFunds, response.getBody().data());
  }

  @Test
  void testRegister_AlreadyRegistered() {
    RegisterDataTransferObject data = new RegisterDataTransferObject("Test Real Estate Fund",
        "TEST",
        "Test Company",
        "Type A",
        100,
        50.5f
    );

    Mockito.when(repository.getBySymbol(anyString())).thenReturn(new RealEstatesFunds());

    ResponseEntity<ResponseDataTransferObject> response = controller.register(data);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("Already registered", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testUpdate() {
    String id = "123456";
    RegisterDataTransferObject data = new RegisterDataTransferObject("Test Real Estate Fund",
        "TEST",
        "Test Company",
        "Type A",
        100,
        50.5f
    );
    RealEstatesFunds updatedFunds = new RealEstatesFunds();

    Mockito.when(service.update(id, data)).thenReturn(Optional.of(updatedFunds));

    ResponseEntity<ResponseDataTransferObject> response = controller.update(id, data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals(updatedFunds, response.getBody().data());
  }

  @Test
  void testUpdate_Error() {
    String id = "123456";
    RegisterDataTransferObject data = new RegisterDataTransferObject("Test Real Estate Fund",
        "TEST",
        "Test Company",
        "Type A",
        100,
        50.5f
    );

    Mockito.when(service.update(id, data)).thenReturn(Optional.empty());

    ResponseEntity<ResponseDataTransferObject> response = controller.update(id, data);

    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("There was an error", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testDelete() {
    String id = "123456";

    ResponseEntity<ResponseDataTransferObject> response = controller.delete(id);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals("successfully deleted", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }
}

