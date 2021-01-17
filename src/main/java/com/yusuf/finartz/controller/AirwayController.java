package com.yusuf.finartz.controller;

import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Airway;
import com.yusuf.finartz.service.AirwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AirwayController {

    @Autowired
    private AirwayService airwayService;

    @GetMapping("/airways")
    public ResponseEntity<List<Airway>> getAllAirways(){
        return ResponseEntity.ok().body(airwayService.getAllAirways());
    }

    @PostMapping("/airways/search")
    public ResponseEntity searchAirports(@RequestBody Airway airway){
        return ResponseEntity.ok().body(airwayService.searchAirways(airway));
    }

    @GetMapping("/airways/{id}")
    public ResponseEntity getAirwayById(@PathVariable long id){
        try {
            return ResponseEntity.ok().body(airwayService.getAirwayById(id));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/airways")
    public ResponseEntity createAirway(@RequestBody Airway airway){
        try {
            return ResponseEntity.ok().body(airwayService.createAirway(airway));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/airways/{id}")
    public ResponseEntity updateAirway(@PathVariable long id, @RequestBody Airway airway){
        airway.setId(id);
        try{
            return ResponseEntity.ok().body(airwayService.updateAirway(airway));
        }
                catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/airways/{id}")
    public ResponseEntity deleteAirway(@PathVariable long id){
        try{
            airwayService.deleteAirway(id);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }
}
