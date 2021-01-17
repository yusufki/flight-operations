package com.yusuf.finartz.service;

import com.yusuf.finartz.exception.RecordNotCreateException;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.model.Airway;
import com.yusuf.finartz.model.Flight;
import com.yusuf.finartz.model.Route;
import com.yusuf.finartz.repository.AirportRepository;
import com.yusuf.finartz.repository.AirwayRepository;
import com.yusuf.finartz.repository.FlightRepository;
import com.yusuf.finartz.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private static final int MAX_SEAT_CAPACITY = 1000;
    private static final String BASE_CURRENCY_CODE = "TL";
    private static final String BASE_FOREIGN_CODE = "USD";

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirwayRepository airwayRepository;

    @Autowired
    RouteRepository routeRepository;

    @Override
    public Flight createFlight(Flight flight) {
       /*
        try{
            return flightRepository.save(flight);
        }catch (Exception e){
            throw new ResourceNotFoundException("Record already exists " + flight.getName());
        }
        */
       if (validateFlightFields(flight)) {
           return flightRepository.save(flight);
       }else {
           throw new RecordNotCreateException("Couldn't create the flight");
       }
    }

    private boolean validateFlightFields(Flight flight) {
        if (flight.getPrice() <= 0 ){
            throw new RecordNotCreateException("Couldn't create the flight with price " + flight.getPrice());
        }
        if (!flight.getCurrency().equals(BASE_CURRENCY_CODE) && !flight.getCurrency().equals(BASE_FOREIGN_CODE)){
            throw new RecordNotCreateException("Couldn't create the flight with currency " + flight.getCurrency());
        }


        if (flight.getSeatCapacity() <= 0 || flight.getSeatCapacity() > MAX_SEAT_CAPACITY){
            throw new RecordNotCreateException("Couldn't create the flight with price " + flight.getPrice());
        }

        if (flight.getFlightDate().isBefore(LocalDateTime.now())){
            throw new RecordNotCreateException("Couldn't create the flight, for date :  " + flight.getFlightDate() );
        }


        Optional<Route> route  = this.routeRepository.findById(flight.getRouteId());
        if (!route.isPresent()){
            throw new RecordNotCreateException("Couldn't create the flight, route is not valid " + flight.getRouteId() );
        }
        Optional<Airway> airway  = this.airwayRepository.findById(flight.getAirwayId());
        if (!airway.isPresent()){
            throw new RecordNotCreateException("Couldn't create the flight, airway is not valid " + flight.getAirwayId() );
        }

        return  true;
    }

    @Override
    public Flight updateFlight(Flight flight) {
        Optional<Flight> flightDb = this.flightRepository.findById(flight.getId());

        if (flightDb.isPresent()){
            Flight flightUpdate = flightDb.get();
            flightUpdate.setId(flight.getId());
            flightUpdate.setSoldSeatCount(flight.getSoldSeatCount());
            flightRepository.save(flightUpdate);
            return flightUpdate;
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + flight.getId());
        }
    }

    @Override
    public List<Flight> getAllFlights() {
        return this.flightRepository.findAll();
    }
    @Override
    public List<Flight> searchFlights(Flight flight) {

        if (flight.getRouteId() > 0 && flight.getAirwayId()>0 && flight.getFlightDate() != null ) {
            return this.flightRepository.findByRouteIdAndAirwayIdAndFlightDate(flight.getRouteId(),flight.getAirwayId(),flight.getFlightDate());
        }
        if (flight.getRouteId() > 0 && flight.getAirwayId()>0 ){
            return this.flightRepository.findByRouteIdAndAirwayId(flight.getRouteId(),flight.getAirwayId());
        }
        else{
            return this.flightRepository.findByRouteId(flight.getRouteId());
        }
    }
    @Override
    public Flight getFlightById(long flightId) {
        Optional<Flight> flightDb = this.flightRepository.findById(flightId);

        if (flightDb.isPresent()){
            return flightDb.get();
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + flightId);
        }
    }


    @Override
    public void deleteFlight(long flightId) {
        Optional<Flight> flightDb = this.flightRepository.findById(flightId);

        if (flightDb.isPresent()){
            this.flightRepository.delete(flightDb.get());
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + flightId);
        }

    }
}
