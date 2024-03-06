package com.mandacarubroker.unitTest.controller;

import com.mandacarubroker.controller.TreasuryController;
import com.mandacarubroker.domain.treasury.GetResponseDataTransferObject;
import com.mandacarubroker.domain.treasury.RegisterDataTransferObject;
import com.mandacarubroker.domain.treasury.ResponseDataTransferObject;
import com.mandacarubroker.domain.treasury.Treasury;
import com.mandacarubroker.repository.TreasuryRepository;
import com.mandacarubroker.service.TreasuryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class TreasuryControllerTest {

  @Mock
  private TreasuryService service;

  @Mock
  private TreasuryRepository repository;

  @InjectMocks
  private TreasuryController controller;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAll() {
    List<Treasury> treasuryList = new ArrayList<>();
    treasuryList.add(new Treasury());
    treasuryList.add(new Treasury());
    treasuryList.add(new Treasury());

    Mockito.when(service.getAll()).thenReturn(treasuryList);

    ResponseEntity<GetResponseDataTransferObject> response = controller.getAll();

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(treasuryList, response.getBody().data());
  }

  @Test
  void testGet() {
    String id = "123456";
    Optional<Treasury> treasury = Optional.of(new Treasury());

    Mockito.when(service.get(id)).thenReturn(treasury);

    ResponseEntity<ResponseDataTransferObject> response = controller.get(id);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertNull(response.getBody().message());
    Assertions.assertEquals(treasury.get(), response.getBody().data());
  }

  @Test
  void testGet_NotFound() {
    String id = "123456";
    Optional<Treasury> treasury = Optional.empty();

    Mockito.when(service.get(id)).thenReturn(treasury);

    ResponseEntity<ResponseDataTransferObject> response = controller.get(id);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertFalse(response.getBody().success());
    Assertions.assertEquals("register not found", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }

  @Test
  void testRegister() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Treasury",
        100,
        "Type A",
        new Timestamp(System.currentTimeMillis()),
        0.05f,
        1000.0f
    );
    Treasury createdTreasury = new Treasury(data);

    Mockito.when(repository.getByName(anyString())).thenReturn(null);
    Mockito.when(service.create(any(RegisterDataTransferObject.class))).thenReturn(createdTreasury);

    ResponseEntity<ResponseDataTransferObject> response = controller.register(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals("successfully registered", response.getBody().message());
    Assertions.assertEquals(createdTreasury, response.getBody().data());
  }

  @Test
  void testRegister_AlreadyRegistered() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Treasury",
        100,
        "Type A",
        new Timestamp(System.currentTimeMillis()),
        0.05f,
        1000.0f
    );

    Mockito.when(repository.getByName(anyString())).thenReturn(new Treasury());

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
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Treasury",
        100,
        "Type A",
        new Timestamp(System.currentTimeMillis()),
        0.05f,
        1000.0f
    );
    Treasury updatedTreasury = new Treasury(data);

    Mockito.when(service.update(id, data)).thenReturn(Optional.of(updatedTreasury));

    ResponseEntity<ResponseDataTransferObject> response = controller.update(id, data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals(updatedTreasury, response.getBody().data());
  }

  @Test
  void testUpdate_Error() {
    String id = "123456";
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Treasury",
        100,
        "Type A",
        new Timestamp(System.currentTimeMillis()),
        0.05f,
        1000.0f
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

