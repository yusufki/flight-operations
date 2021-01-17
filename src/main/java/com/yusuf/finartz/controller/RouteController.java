package com.yusuf.finartz.controller;

import com.yusuf.finartz.exception.RecordNotCreateException;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Route;
import com.yusuf.finartz.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/routes")
    public ResponseEntity<List<Route>> getAllRoutes(){
        return ResponseEntity.ok().body(routeService.getAllRoutes());
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity getRouteById(@PathVariable long id){
        try {
            return ResponseEntity.ok().body(routeService.getRouteById(id));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/routes/search")
    public ResponseEntity<List<Route>>  SearchRoute(@RequestBody Route route){
            return ResponseEntity.ok().body(routeService.searchRoutes(route));
    }

    @PostMapping("/routes")
    public ResponseEntity createRoute(@RequestBody Route route){
        try {
            return ResponseEntity.ok().body(routeService.createRoute(route));
        }
        catch(RecordNotCreateException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/routes/{id}")
    public ResponseEntity updateRoute(@PathVariable long id, @RequestBody Route route){
        route.setId(id);
        try{
            return ResponseEntity.ok().body(routeService.updateRoute(route));
        }
                catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/routes/{id}")
    public ResponseEntity deleteRoute(@PathVariable long id){
        try{
            routeService.deleteRoute(id);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }
}
