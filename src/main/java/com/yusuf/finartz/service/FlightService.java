package com.yusuf.finartz.service;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Flight;
import com.yusuf.finartz.model.FlightDTO;

import java.util.List;

public interface FlightService {

    ResultBean<List<Flight>> findAll();

    ResultBean<Flight> findById(long id);

    Result createFlight(FlightDTO flightDTO);

    ResultBean<List<Flight>> searchFlights(FlightDTO flightDTO);
}
