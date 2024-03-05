package com.mandacarubroker.unitTest.service;

import com.mandacarubroker.domain.realestatesfunds.RealEstatesFunds;
import com.mandacarubroker.domain.realestatesfunds.RegisterDataTransferObject;
import com.mandacarubroker.repository.RealEstatesFundsRepository;
import com.mandacarubroker.service.RealEstatesFundsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class RealEstatesFundsServiceTest {

  @Mock
  private RealEstatesFundsRepository realEstatesFundsRepository;

  @InjectMocks
  private RealEstatesFundsService realEstatesFundsService;

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

    Mockito.when(realEstatesFundsRepository.findAll()).thenReturn(fundsList);

    List<RealEstatesFunds> result = realEstatesFundsService.getAll();

    Assertions.assertEquals(fundsList.size(), result.size());
  }

  @Test
  void testGet() {
    RealEstatesFunds fund = new RealEstatesFunds();
    String id = "123456";

    Mockito.when(realEstatesFundsRepository.findById(id)).thenReturn(Optional.of(fund));

    Optional<RealEstatesFunds> result = realEstatesFundsService.get(id);

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(fund, result.get());
  }

  @Test
  void testCreate() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Real Estate Fund",
        "TEST",
        "Test Company",
        "Type A",
        100,
        50.5f
    );

    RealEstatesFunds fund = realEstatesFundsService.create(data);

    Mockito.verify(realEstatesFundsRepository, Mockito.times(1)).save(any(RealEstatesFunds.class));
  }

  @Test
  void testUpdate() {
    String id = "123456";
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Real Estate Fund",
        "TEST",
        "Test Company",
        "Type A",
        100,
        50.5f
    );;

    RealEstatesFunds fund = new RealEstatesFunds();
    Mockito.when(realEstatesFundsRepository.findById(id)).thenReturn(Optional.of(fund));

    realEstatesFundsService.update(id, data);

    Mockito.verify(realEstatesFundsRepository, Mockito.times(1)).save(any(RealEstatesFunds.class));
  }

  @Test
  void testDelete() {
    String id = "123456";

    realEstatesFundsService.delete(id);

    Mockito.verify(realEstatesFundsRepository, Mockito.times(1)).deleteById(id);
  }
}
