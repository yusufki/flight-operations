package com.yusuf.finartz.service;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Route;
import com.yusuf.finartz.model.RouteDTO;

import java.util.List;

public interface RouteService {

    ResultBean<List<Route>> findAll();

    ResultBean<Route> findById(long id);

    Result createRoute(RouteDTO routeDTO);

    ResultBean<List<Route>> searchRoutes(RouteDTO routeDTO);
}
