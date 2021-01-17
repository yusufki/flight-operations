package com.yusuf.finartz.controller;

import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> getAllAirports(){
        return ResponseEntity.ok().body(airportService.getAllAirports());
    }

    @GetMapping("/airports/{id}")
    public ResponseEntity getAirportById(@PathVariable long id){
        try {
            return ResponseEntity.ok().body(airportService.getAirportById(id));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/airports")
    public ResponseEntity createAirport(@RequestBody Airport airport){
        try {
            return ResponseEntity.ok().body(airportService.createAirport(airport));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/airports/search")
    public ResponseEntity searchAirports(@RequestBody Airport airport){
        return ResponseEntity.ok().body(airportService.searchAirports(airport));
    }

    @PutMapping("/airports/{id}")
    public ResponseEntity updateAirport(@PathVariable long id, @RequestBody Airport airport){
        airport.setId(id);
        try{
            return ResponseEntity.ok().body(airportService.updateAirport(airport));
        }
                catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/airports/{id}")
    public ResponseEntity deleteAirport(@PathVariable long id){
        try{
            airportService.deleteAirport(id);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }
}
