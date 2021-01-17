package com.yusuf.finartz.service;

import com.yusuf.finartz.model.Flight;

import java.util.List;

public interface FlightService {

    Flight createFlight(Flight flight);

    Flight updateFlight(Flight flight);

    List<Flight> getAllFlights();

    Flight getFlightById(long flightId);

    void deleteFlight(long flightId);

    List<Flight> searchFlights(Flight flight);

}
