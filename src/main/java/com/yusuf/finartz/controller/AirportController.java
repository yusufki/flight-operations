package com.yusuf.finartz.controller;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.bean.ResultStatus;
import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.model.AirportDTO;
import com.yusuf.finartz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/airport")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("/findAll")
    public ResultBean<List<Airport>> findAll() {
        return airportService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ResultBean<Airport> findById(@PathVariable long id) {
        return airportService.findById(id);
    }

    @GetMapping("/findByName/{name}")
    public ResultBean<Airport> findByName(@PathVariable String name) {
        return airportService.findByName(name);
    }

    @PostMapping("/create")
    public Result createAirport(@RequestBody AirportDTO airportDTO) {
        return airportService.createAirport(airportDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleException(RuntimeException ex){
        Result result = new Result();
        result.setStatus(ResultStatus.FAIL).setErrorCode("AIRPORT_NOT_UNIQE");
        result.setMessage("Record not created with name : " );
        return new ResponseEntity<Result>(result,HttpStatus.CONFLICT);
    }
}
