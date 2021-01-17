package com.yusuf.finartz.controller;

import com.yusuf.finartz.exception.RecordNotCreateException;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Flight;
import com.yusuf.finartz.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights(){
        return ResponseEntity.ok().body(flightService.getAllFlights());
    }

    @PostMapping("/flights/search")
    public ResponseEntity searchAirports(@RequestBody Flight flight){
        return ResponseEntity.ok().body(flightService.searchFlights(flight));
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity getFlightById(@PathVariable long id){
        try {
            return ResponseEntity.ok().body(flightService.getFlightById(id));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/flights")
    public ResponseEntity createFlight(@RequestBody Flight flight){
        try {
            return ResponseEntity.ok().body(flightService.createFlight(flight));
        }
        catch(RecordNotCreateException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/flights/{id}")
    public ResponseEntity updateFlight(@PathVariable long id, @RequestBody Flight flight){
        flight.setId(id);
        try{
            return ResponseEntity.ok().body(flightService.updateFlight(flight));
        }
                catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/flights/{id}")
    public ResponseEntity deleteFlight(@PathVariable long id){
        try{
            flightService.deleteFlight(id);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }
}
