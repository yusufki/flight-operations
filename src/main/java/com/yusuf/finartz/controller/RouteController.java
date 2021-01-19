package com.yusuf.finartz.controller;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Route;
import com.yusuf.finartz.model.RouteDTO;
import com.yusuf.finartz.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/findAll")
    public ResultBean<List<Route>> findAll() {
        return routeService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ResultBean<Route> findById(@PathVariable long id) {
        return routeService.findById(id);
    }

    @PostMapping("/create")
    public Result createRoute(@RequestBody RouteDTO routeDTO) {
        return routeService.createRoute(routeDTO);
    }

    @PostMapping("/search")
    public ResultBean<List<Route>> searchRoutes(@RequestBody RouteDTO routeDTO) {
        return routeService.searchRoutes(routeDTO);
    }
}
