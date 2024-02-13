package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StockServiceTest{
    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private StockService stockService;
    private Stock stock;
    private String existingId,nonExistingId;

    @BeforeEach
    void setup(){
        stock = new Stock("1","ABC1","Banco do Brasil Sa",50.0);
        existingId = "1";
        nonExistingId = "none";
    }

    @Test
    void findAllShouldReturnAStockList(){
        when(stockRepository.findAll()).thenReturn(List.of(stock));
        List<Stock> stocks = stockService.getAllStocks();
        verify(stockRepository).findAll();
        assertFalse(stocks.isEmpty());
    }
    @Test
    void getStockByIdShouldReturnAStockWhenExistingId(){
        when(stockRepository.findById(existingId)).thenReturn(Optional.of(stock));

        Stock actual = stockService.getStockById(existingId);

        verify(stockRepository,only()).findById(any());
        assertNotNull(actual);
        assertEquals(stock.getId(),actual.getId());
        assertEquals(stock.getCompanyName(),actual.getCompanyName());
        assertEquals(stock.getSymbol(),actual.getSymbol());
        assertEquals(stock.getPrice(),actual.getPrice());
    }

    @Test
    void getStockByIdShouldThrowEntityNotFoundWhenNonExistingId(){
        when(stockRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->stockService.getStockById(nonExistingId));

        verify(stockRepository,only()).findById(any());
    }

    @Test
    void createShouldReturnAStock(){
        when(stockRepository.save(any())).thenReturn(stock);
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(), stock.getCompanyName(),stock.getPrice());
        Stock saved = stockService.createStock(validDto);

        assertEquals(validDto.symbol(),saved.getSymbol());
        assertEquals(validDto.companyName(),saved.getCompanyName());
        assertEquals(validDto.price(),saved.getPrice());

    }

    @Test
    void createShouldThrowExceptionWhenInvalidStock(){
        when(stockRepository.save(any())).thenReturn(stock);
        RequestStockDTO invalidDto = new RequestStockDTO("a","",-1);
        assertThrows(ConstraintViolationException.class,()->stockService.createStock(invalidDto));
    }

    @Test
    void updateShouldReturnAStockWhenExistingId(){
        when(stockRepository.findById(existingId)).thenReturn(Optional.ofNullable(stock));
        when(stockRepository.save(any())).thenReturn(stock);
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(), stock.getCompanyName(),stock.getPrice());
        double expectedPrice = stock.getPrice()+stock.getPrice();

        Stock updatedStock = stockService.updateStock(existingId,validDto);

        assertNotNull(updatedStock);
        assertEquals(validDto.symbol(),updatedStock.getSymbol());
        assertEquals(validDto.companyName(),updatedStock.getCompanyName());
        assertEquals(expectedPrice,updatedStock.getPrice());
    }

    @Test
    void updateShouldThrowExceptionWhenNonexistingId(){
        when(stockRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(), stock.getCompanyName(),stock.getPrice());

        assertThrows(EntityNotFoundException.class,()->stockService.updateStock(nonExistingId,validDto));
    }

    @Test
    void deleteShouldDoNothing(){
        when(stockRepository.existsById(existingId)).thenReturn(true);
        doNothing().when(stockRepository).deleteById(anyString());
        assertDoesNotThrow(()->stockService.deleteStock(existingId));
    }

    @Test
    void deleteShouldThrowExceptionWhenNonExistingId(){
        doReturn(false).when(stockRepository).existsById(nonExistingId);
        assertThrows(EntityNotFoundException.class,()->stockService.deleteStock(existingId));
        verify(stockRepository,never()).deleteById(anyString());
    }

    @Test
    void validateStockDTOShouldDoNothingWhenValidDTO(){
        RequestStockDTO validDto = new RequestStockDTO(stock.getSymbol(), stock.getCompanyName(),stock.getPrice());
        assertDoesNotThrow(()->stockService.validateRequestStockDTO(validDto));
    }

    @Test
    void validateStockDTOShouldThrowExceptionWhenInvalidDTO(){
        RequestStockDTO invalidDto = new RequestStockDTO("A","",0);
        int expectedViolations = 3;
        ConstraintViolationException e = assertThrows(ConstraintViolationException.class,()->stockService.validateRequestStockDTO(invalidDto));
        assertEquals(expectedViolations,e.getConstraintViolations().size());
    }
}