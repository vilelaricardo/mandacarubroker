package com.mandacarubroker.unitTest.controller;

import com.mandacarubroker.controller.InvestmentsController;
import com.mandacarubroker.domain.typesinvestiments.GetResponseDataTransferObject;
import com.mandacarubroker.domain.typesinvestiments.RegisterTypeInvestment;
import com.mandacarubroker.domain.typesinvestiments.ResponseDataTransferObject;
import com.mandacarubroker.domain.typesinvestiments.TypesInvestments;
import com.mandacarubroker.service.TypesInvestmentsService;
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

import static org.mockito.ArgumentMatchers.any;

class InvestmentsControllerTest {

  @Mock
  private TypesInvestmentsService investmentsService;

  @InjectMocks
  private InvestmentsController investmentsController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAll() {
    List<TypesInvestments> investmentsList = new ArrayList<>();
    investmentsList.add(new TypesInvestments());
    investmentsList.add(new TypesInvestments());
    investmentsList.add(new TypesInvestments());

    Mockito.when(investmentsService.getAllInvestments()).thenReturn(investmentsList);

    ResponseEntity<GetResponseDataTransferObject> response = investmentsController.getAll();

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(investmentsList, response.getBody().data());
  }

  @Test
  void testCreate() {
    RegisterTypeInvestment data = new RegisterTypeInvestment("Test Investment Type");
    TypesInvestments createdInvestment = new TypesInvestments(data);

    Mockito.when(investmentsService.create(any(RegisterTypeInvestment.class))).thenReturn(createdInvestment);

    ResponseEntity<ResponseDataTransferObject> response = investmentsController.create(data);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals("successfully registered", response.getBody().message());
    Assertions.assertEquals(createdInvestment, response.getBody().data());
  }

  @Test
  void testDelete() {
    String id = "123456";

    ResponseEntity<ResponseDataTransferObject> response = investmentsController.delete(id);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().success());
    Assertions.assertEquals("successfully deleted", response.getBody().message());
    Assertions.assertNull(response.getBody().data());
  }
}

