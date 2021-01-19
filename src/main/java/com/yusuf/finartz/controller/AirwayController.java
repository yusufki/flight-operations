package com.yusuf.finartz.controller;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Airway;
import com.yusuf.finartz.model.AirwayDTO;
import com.yusuf.finartz.service.AirwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/airway")
public class AirwayController {

    @Autowired
    private AirwayService airwayService;

    @GetMapping("/findAll")
    public ResultBean<List<Airway>> findAll() {
        return airwayService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ResultBean<Airway> findById(@PathVariable long id) {
        return airwayService.findById(id);
    }

    @GetMapping("/findByName/{name}")
    public ResultBean<Airway> findByName(@PathVariable String name) {
        return airwayService.findByName(name);
    }

    @PostMapping("/create")
    public Result createAirport(@RequestBody AirwayDTO airwayDTO) {
        return airwayService.createAirway(airwayDTO);
    }

}
