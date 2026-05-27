package com.rbu.service;

import com.rbu.entity.Flight;
import com.rbu.repo.FlightRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight findbyCode(String code) {
        return flightRepository.findById(code).orElse(null);
    }

    public List<Flight> findbyCarrier(String car) {
        return flightRepository.findByCarrier(car);
    }

    public List<Flight> findbyRoutes(String src, String dest) {
        return flightRepository.findBySourceAndDestination(src, dest);
    }

    public List<Flight> findbyPriceRange(double min, double max) {
        return flightRepository.findByCostBetween(min, max);
    }

    public List<Flight> list() {
        return flightRepository.findAll();
    }

    public String delete(String code) {
        flightRepository.deleteById(code);
        return "Flight deleted";
    }
}
