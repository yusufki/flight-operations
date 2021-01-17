package com.yusuf.finartz.service;

import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirportServiceImpl implements AirportService {

    @Autowired
    AirportRepository airportRepository;

    @Override
    public Airport createAirport(Airport airport) {
       /*
        try{
            return airportRepository.save(airport);
        }catch (Exception e){
            throw new ResourceNotFoundException("Record already exists " + airport.getName());
        }
        */

       return   airportRepository.save(airport);

    }

    @Override
    public Airport updateAirport(Airport airport) {
        Optional<Airport> airportDb = this.airportRepository.findById(airport.getId());

        if (airportDb.isPresent()){
            Airport airportUpdate = airportDb.get();
            airportUpdate.setId(airport.getId());
            airportUpdate.setName(airport.getName());
            airportRepository.save(airportUpdate);
            return airportUpdate;
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + airport.getId());
        }
    }

    @Override
    public List<Airport> getAllAirports() {
        return this.airportRepository.findAll();
    }

    @Override
    public Airport getAirportById(long airportId) {
        Optional<Airport> airportDb = this.airportRepository.findById(airportId);

        if (airportDb.isPresent()){
            return airportDb.get();
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + airportId);
        }
    }

    @Override
    public List<Airport> searchAirports(Airport airport) {

       return this.airportRepository.findByName(airport.getName());

    }

    @Override
    public void deleteAirport(long airportId) {
        Optional<Airport> airportDb = this.airportRepository.findById(airportId);

        if (airportDb.isPresent()){
            this.airportRepository.delete(airportDb.get());
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + airportId);
        }

    }
}
