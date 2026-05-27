package com.rbu.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rbu.entity.Flight;
import com.rbu.repo.FlightRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    void setUp() {
        flight1 = new Flight("FL123", "Delta", "New York", "London", 500.0);
        flight2 = new Flight("FL456", "United", "New York", "Paris", 600.0);
    }

    @Test
    void testSave() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight1);
        
        Flight saved = flightService.save(flight1);
        
        assertNotNull(saved);
        assertEquals("FL123", saved.getCode());
        assertEquals("Delta", saved.getCarrier());
        verify(flightRepository, times(1)).save(flight1);
    }

    @Test
    void testFindbyCode() {
        when(flightRepository.findById("FL123")).thenReturn(Optional.of(flight1));
        
        Flight found = flightService.findbyCode("FL123");
        
        assertNotNull(found);
        assertEquals("FL123", found.getCode());
        verify(flightRepository, times(1)).findById("FL123");
    }

    @Test
    void testFindbyCode_NotFound() {
        when(flightRepository.findById("NON_EXISTENT")).thenReturn(Optional.empty());
        
        Flight found = flightService.findbyCode("NON_EXISTENT");
        
        assertNull(found);
        verify(flightRepository, times(1)).findById("NON_EXISTENT");
    }

    @Test
    void testFindbyCarrier() {
        when(flightRepository.findByCarrier("Delta")).thenReturn(Arrays.asList(flight1));
        
        List<Flight> results = flightService.findbyCarrier("Delta");
        
        assertEquals(1, results.size());
        assertEquals("Delta", results.get(0).getCarrier());
        verify(flightRepository, times(1)).findByCarrier("Delta");
    }

    @Test
    void testFindbyRoutes() {
        when(flightRepository.findBySourceAndDestination("New York", "London")).thenReturn(Arrays.asList(flight1));
        
        List<Flight> results = flightService.findbyRoutes("New York", "London");
        
        assertEquals(1, results.size());
        assertEquals("New York", results.get(0).getSource());
        assertEquals("London", results.get(0).getDestination());
        verify(flightRepository, times(1)).findBySourceAndDestination("New York", "London");
    }

    @Test
    void testFindbyPriceRange() {
        when(flightRepository.findByCostBetween(400.0, 700.0)).thenReturn(Arrays.asList(flight1, flight2));
        
        List<Flight> results = flightService.findbyPriceRange(400.0, 700.0);
        
        assertEquals(2, results.size());
        verify(flightRepository, times(1)).findByCostBetween(400.0, 700.0);
    }

    @Test
    void testList() {
        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2));
        
        List<Flight> results = flightService.list();
        
        assertEquals(2, results.size());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testDelete() {
        doNothing().when(flightRepository).deleteById("FL123");
        
        String result = flightService.delete("FL123");
        
        assertEquals("Flight deleted", result);
        verify(flightRepository, times(1)).deleteById("FL123");
    }
}
