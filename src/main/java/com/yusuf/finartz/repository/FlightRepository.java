package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Flight findById(long id);

    List<Flight> findByRouteId(long routeId);

    List<Flight> findByFlightDate(LocalDateTime flightDate);

    List<Flight> findByRouteIdAndAirwayIdAndFlightDate(long routeId, long airwayId, LocalDateTime flightDate);

    List<Flight> findByRouteIdAndFlightDate(long routeId, LocalDateTime flightDate);


}
