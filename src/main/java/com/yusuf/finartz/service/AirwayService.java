package com.yusuf.finartz.service;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Airway;
import com.yusuf.finartz.model.AirwayDTO;

import java.util.List;

public interface AirwayService {

    Result createAirway(AirwayDTO airwayDTO);

    ResultBean<List<Airway>> findAll();

    ResultBean<Airway> findById(long airportId);

    ResultBean<Airway> findByName(String airwayName);

}
