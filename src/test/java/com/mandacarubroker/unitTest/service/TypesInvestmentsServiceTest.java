package com.mandacarubroker.unitTest.service;

import com.mandacarubroker.domain.typesinvestiments.RegisterTypeInvestment;
import com.mandacarubroker.domain.typesinvestiments.TypesInvestments;
import com.mandacarubroker.repository.TypesInvestmentsRepository;
import com.mandacarubroker.service.TypesInvestmentsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class TypesInvestmentsServiceTest {

  @Mock
  private TypesInvestmentsRepository investmentsRepository;

  @InjectMocks
  private TypesInvestmentsService typesInvestmentsService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllInvestments() {
    List<TypesInvestments> investmentsList = new ArrayList<>();
    investmentsList.add(new TypesInvestments());
    investmentsList.add(new TypesInvestments());
    investmentsList.add(new TypesInvestments());

    Mockito.when(investmentsRepository.findAll()).thenReturn(investmentsList);

    List<TypesInvestments> result = typesInvestmentsService.getAllInvestments();

    Assertions.assertEquals(investmentsList.size(), result.size());
  }

  @Test
  void testCreate() {
    RegisterTypeInvestment data = new RegisterTypeInvestment("Test Investment Type");

    TypesInvestments investments = typesInvestmentsService.create(data);

    Mockito.verify(investmentsRepository, Mockito.times(1)).save(any(TypesInvestments.class));
  }

  @Test
  void testDelete() {
    String id = "123456";

    typesInvestmentsService.delete(id);

    Mockito.verify(investmentsRepository, Mockito.times(1)).deleteById(id);
  }
}
