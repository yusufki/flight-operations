package com.yusuf.finartz.service;

import com.yusuf.finartz.model.Route;

import java.util.List;

public interface RouteService {

    Route createRoute(Route route);

    Route updateRoute(Route route);

    List<Route> getAllRoutes();

    List<Route> searchRoutes(Route route);

    Route getRouteById(long routeId);

    void deleteRoute(long routeId);
}
