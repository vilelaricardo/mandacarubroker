package com.mandacarubroker.unitTest.service;

import com.mandacarubroker.domain.treasury.RegisterDataTransferObject;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class TreasuryServiceTest {

  @Mock
  private TreasuryRepository treasuryRepository;

  @InjectMocks
  private TreasuryService treasuryService;

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

    Mockito.when(treasuryRepository.findAll()).thenReturn(treasuryList);

    List<Treasury> result = treasuryService.getAll();

    Assertions.assertEquals(treasuryList.size(), result.size());
  }

  @Test
  void testGet() {
    Treasury treasury = new Treasury();
    String id = "123456";

    Mockito.when(treasuryRepository.findById(id)).thenReturn(Optional.of(treasury));

    Optional<Treasury> result = treasuryService.get(id);

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(treasury, result.get());
  }

  @Test
  void testCreate() {
    RegisterDataTransferObject data = new RegisterDataTransferObject(
        "Test Treasury",
        100,
        "Type A",
        new Timestamp(System.currentTimeMillis()),
        0.05f,
        1000.0f
    );

    Treasury treasury = treasuryService.create(data);

    Mockito.verify(treasuryRepository, Mockito.times(1)).save(any(Treasury.class));
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

    Treasury treasury = new Treasury();
    Mockito.when(treasuryRepository.findById(id)).thenReturn(Optional.of(treasury));

    treasuryService.update(id, data);

    Mockito.verify(treasuryRepository, Mockito.times(1)).save(any(Treasury.class));
  }

  @Test
  void testDelete() {
    String id = "123456";

    treasuryService.delete(id);

    Mockito.verify(treasuryRepository, Mockito.times(1)).deleteById(id);
  }
}
