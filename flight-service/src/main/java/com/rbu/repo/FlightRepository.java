package com.rbu.repo;

import com.rbu.entity.Flight;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, String> {

    List<Flight> findByCarrier(String carrier);

    List<Flight> findBySourceAndDestination(String source, String destination);

    List<Flight> findByCostBetween(double min, double max);
}
