package com.yusuf.finartz.service;

import com.yusuf.finartz.exception.RecordNotCreateException;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.model.Route;
import com.yusuf.finartz.repository.AirportRepository;
import com.yusuf.finartz.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AirportRepository airportRepository;

    @Override
    public Route createRoute(Route route) {
       /*
        try{
            return routeRepository.save(route);
        }catch (Exception e){
            throw new ResourceNotFoundException("Record already exists " + route.getName());
        }
        */
       if (validateRouteFields(route)) {
           return routeRepository.save(route);
       }else {
           throw new RecordNotCreateException("Couldn't create the route, check departure and arrival airport ids " + route.getFromAirportId() + " " + route.getToAirportId());
       }
    }

    private boolean validateRouteFields(Route route) {
        if (route.getFromAirportId() == route.getToAirportId()){
            throw new RecordNotCreateException("Couldn't create the route, departure and arrival airports are the same");
        }
        Optional<Airport> airportFrom = this.airportRepository.findById(route.getFromAirportId());
        if (!airportFrom.isPresent()){
            throw new RecordNotCreateException("Couldn't create the route, departure airport is not valid " + route.getFromAirportId() );
        }
        Optional<Airport> airportTo  = this.airportRepository.findById(route.getToAirportId());
        if (!airportTo.isPresent()){
            throw new RecordNotCreateException("Couldn't create the route, arrival airport is not valid " + route.getToAirportId() );
        }
        return  true;
    }

    @Override
    public Route updateRoute(Route route) {
        Optional<Route> routeDb = this.routeRepository.findById(route.getId());

        if (routeDb.isPresent()){
            Route routeUpdate = routeDb.get();
            routeUpdate.setId(route.getId());
            routeUpdate.setFromAirportId(route.getFromAirportId());
            routeUpdate.setToAirportId(route.getToAirportId());
            routeRepository.save(routeUpdate);
            return routeUpdate;
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + route.getId());
        }
    }

    @Override
    public List<Route> getAllRoutes() {
        return this.routeRepository.findAll();
    }

    @Override
    public List<Route> searchRoutes(Route route) {
        if (route.getFromAirportId() > 0 && route.getToAirportId()>0 ) {
            return this.routeRepository.findByFromAirportIdAndToAirportId(route.getFromAirportId(),route.getToAirportId());
        }
        if (route.getFromAirportId() > 0){
            return this.routeRepository.findByFromAirportId(route.getFromAirportId());
        }
        else{
            return this.routeRepository.findByToAirportId(route.getToAirportId());
        }
    }

    @Override
    public Route getRouteById(long routeId) {
        Optional<Route> routeDb = this.routeRepository.findById(routeId);

        if (routeDb.isPresent()){
            return routeDb.get();
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + routeId);
        }
    }


    @Override
    public void deleteRoute(long routeId) {
        Optional<Route> routeDb = this.routeRepository.findById(routeId);

        if (routeDb.isPresent()){
            this.routeRepository.delete(routeDb.get());
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + routeId);
        }

    }
}
