package com.yusuf.finartz.controller;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Flight;
import com.yusuf.finartz.model.FlightDTO;
import com.yusuf.finartz.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/findAll")
    public ResultBean<List<Flight>> findAll() {
        return flightService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ResultBean<Flight> findById(@PathVariable long id) {
        return flightService.findById(id);
    }

    @PostMapping("/create")
    public Result createFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.createFlight(flightDTO);
    }

    @PostMapping("/search")
    public ResultBean<List<Flight>> searchAirports(@RequestBody FlightDTO flightDTO) {
        return flightService.searchFlights(flightDTO);
    }
}
