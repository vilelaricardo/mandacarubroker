package com.mandacarubroker.unitTest.service;

import com.mandacarubroker.domain.etf.ETF;
import com.mandacarubroker.domain.etf.RegisterDataTransferObject;
import com.mandacarubroker.repository.ETFRepository;
import com.mandacarubroker.service.ETFService;
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

class ETFServiceTest {

  @Mock
  private ETFRepository etfRepository;

  @InjectMocks
  private ETFService etfService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAll() {
    List<ETF> etfList = new ArrayList<>();
    etfList.add(new ETF(new RegisterDataTransferObject(
        "Test ETF",
        "TEST2",
        "Test Company",
        "funds2",
        3,
        30.03f
    )));
    etfList.add(new ETF(new RegisterDataTransferObject(
        "Test ETF2",
        "TEST2",
        "Test Company",
        "funds",
        2,
        30.03f
    )));
    etfList.add(new ETF(new RegisterDataTransferObject(
        "Test ETF3",
        "TEST3",
        "Test Company",
        "funds",
        2,
        30.03f
    )));

    Mockito.when(etfRepository.findAll()).thenReturn(etfList);

    List<ETF> result = etfService.getAll();

    Assertions.assertEquals(etfList.size(), result.size());
  }

  @Test
  void testGet() {
    ETF etf = new ETF(new RegisterDataTransferObject(
        "Test ETF",
        "TEST",
        "Test Company",
        "funds",
        2,
        30.03f
    ));
    String id = "123456";

    Mockito.when(etfRepository.findById(id)).thenReturn(Optional.of(etf));

    Optional<ETF> result = etfService.get(id);

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(etf, result.get());
  }

  @Test
  void testCreate() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test ETF",
        "TEST",
        "Test Company",
        "funds",
        2,
        30.03f
    );;

    ETF etf = etfService.create(data);

    Mockito.verify(etfRepository, Mockito.times(1)).save(any(ETF.class));
  }

  @Test
  void testUpdate() {
    String id = "123456";
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test ETF",
        "TEST",
        "Test Company",
        "funds",
        2,
        30.03f
    );;

    ETF etf = new ETF();
    Mockito.when(etfRepository.findById(id)).thenReturn(Optional.of(etf));

    etfService.update(id, data);

    Mockito.verify(etfRepository, Mockito.times(1)).save(any(ETF.class));
  }

  @Test
  void testDelete() {
    String id = "123456";

    etfService.delete(id);

    Mockito.verify(etfRepository, Mockito.times(1)).deleteById(id);
  }
}
