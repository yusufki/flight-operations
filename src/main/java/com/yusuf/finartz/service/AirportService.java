package com.yusuf.finartz.service;

import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.model.Airway;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface AirportService {

    Airport createAirport(Airport airport);

    Airport updateAirport(Airport airport);

    List<Airport> getAllAirports();

    List<Airport> searchAirports(Airport airport);

    Airport getAirportById(long airportId);

    void deleteAirport(long airportId);

}
