package com.yusuf.finartz.service.impl;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.bean.ResultStatus;
import com.yusuf.finartz.model.Route;
import com.yusuf.finartz.model.RouteDTO;
import com.yusuf.finartz.repository.AirportRepository;
import com.yusuf.finartz.repository.RouteRepository;
import com.yusuf.finartz.service.RouteService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AirportRepository airportRepository;


    @Override
    public ResultBean<List<Route>> findAll() {
        ResultBean<List<Route>> resultBean = new ResultBean<>();
        List<Route> routeList = routeRepository.findAll();

        resultBean.setData(routeList);
        resultBean.setStatus(ResultStatus.OK);

        return resultBean;
    }

    @Override
    public ResultBean<Route> findById(long id) {
        ResultBean<Route> result = new ResultBean<>(ResultStatus.OK);
        Route route = routeRepository.findById(id);

        if (route == null) {
            result.setStatus(ResultStatus.FAIL).setErrorCode("ROUTE_NOT_FOUND");
            result.setMessage("Record not found with id : " + id);
        } else {
            result.setData(route);
        }

        return result;
    }

    @Override
    public Result createRoute(RouteDTO routeDTO) {
        Result result = validateRouteFields(routeDTO);

        if (result.isOk()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Route route = modelMapper.map(routeDTO, Route.class);
            routeRepository.save(route);
        }
        return result;

    }

    private Result validateRouteFields(RouteDTO routeDTO) {
        Result result = new Result().setStatus(ResultStatus.FAIL);
        if (routeDTO.getFromAirport().getId() == routeDTO.getToAirport().getId()) {
            result.setErrorCode("INVALID_ROUTE");
            result.setMessage("Couldn't create the route, departure and arrival airports are the same");
        } else if (airportRepository.findById(routeDTO.getFromAirport().getId()) == null) {
            result.setErrorCode("INVALID_DEPARTURE");
            result.setMessage("Couldn't create the route, departure airport is not valid " + routeDTO.getFromAirport().getId());
        } else if (airportRepository.findById(routeDTO.getToAirport().getId()) == null) {
            result.setErrorCode("INVALID_ARRIVAL");
            result.setMessage("Couldn't create the route, arrival airport is not valid " + routeDTO.getToAirport().getId());
        }

        return result.getErrorCode() == null ? result.setStatus(ResultStatus.OK) : result;
    }

    @Override
    public ResultBean<List<Route>> searchRoutes(RouteDTO routeDTO) {
        ResultBean<List<Route>> resultBean = new ResultBean<>();
        List<Route> routeList;

        if (routeDTO.getFromAirport().getId() > 0 && routeDTO.getToAirport().getId() > 0) {
            routeList = routeRepository.findByFromAirportIdAndToAirportId(routeDTO.getFromAirport().getId(), routeDTO.getToAirport().getId());
        }
        if (routeDTO.getFromAirport().getId() > 0) {
            routeList = routeRepository.findByFromAirportId(routeDTO.getFromAirport().getId());
        } else {
            routeList = routeRepository.findByToAirportId(routeDTO.getToAirport().getId());
        }

        resultBean.setData(routeList);
        resultBean.setStatus(ResultStatus.OK);

        return resultBean;

    }

}
