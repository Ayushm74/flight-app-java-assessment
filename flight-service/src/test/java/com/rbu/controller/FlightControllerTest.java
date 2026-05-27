package com.rbu.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbu.entity.Flight;
import com.rbu.service.FlightService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Autowired
    private ObjectMapper objectMapper;

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight("FL123", "Delta", "New York", "London", 500.0);
    }

    @Test
    void testAddFlight() throws Exception {
        when(flightService.save(any(Flight.class))).thenReturn(flight);

        mockMvc.perform(post("/flight/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("FL123"))
                .andExpect(jsonPath("$.carrier").value("Delta"))
                .andExpect(jsonPath("$.cost").value(500.0));
    }

    @Test
    void testGetFlight() throws Exception {
        when(flightService.findbyCode("FL123")).thenReturn(flight);

        mockMvc.perform(get("/flight/code/FL123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("FL123"));
    }

    @Test
    void testGetAllFlights() throws Exception {
        when(flightService.list()).thenReturn(Arrays.asList(flight));

        mockMvc.perform(get("/flight/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("FL123"));
    }
}
