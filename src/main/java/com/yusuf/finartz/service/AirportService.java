package com.yusuf.finartz.service;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.model.AirportDTO;

import java.util.List;

public interface AirportService {
    Result createAirport(AirportDTO airportDTO);

    ResultBean<List<Airport>> findAll();

    ResultBean<Airport> findById(long airportId);

    ResultBean<Airport> findByName(String airwayName);

}
