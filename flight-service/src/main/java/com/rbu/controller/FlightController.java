package com.rbu.controller;

import com.rbu.entity.Flight;
import com.rbu.service.FlightService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
@CrossOrigin(origins = "*")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/add")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }

    @GetMapping("/code/{code}")
    public Flight getFlight(@PathVariable String code) {
        return flightService.findbyCode(code);
    }

    @GetMapping("/carrier/{car}")
    public List<Flight> getFlightsByCarrier(@PathVariable String car) {
        return flightService.findbyCarrier(car);
    }

    @GetMapping("/routes/{src}/{dest}")
    public List<Flight> getFlightsByRoutes(@PathVariable String src, @PathVariable String dest) {
        return flightService.findbyRoutes(src, dest);
    }

    @GetMapping("/price/{min}/{max}")
    public List<Flight> getFlightsByPriceRange(@PathVariable double min, @PathVariable double max) {
        return flightService.findbyPriceRange(min, max);
    }

    @GetMapping("/all")
    public List<Flight> getAllFlights() {
        return flightService.list();
    }

    @DeleteMapping("/delete/{code}")
    public String deleteFlight(@PathVariable String code) {
        return flightService.delete(code);
    }
}
