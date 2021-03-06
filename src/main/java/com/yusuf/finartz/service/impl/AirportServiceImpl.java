package com.yusuf.finartz.service.impl;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.bean.ResultStatus;
import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.model.AirportDTO;
import com.yusuf.finartz.repository.AirportRepository;
import com.yusuf.finartz.service.AirportService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private
    AirportRepository airportRepository;

    @Override
    public Result createAirport(AirportDTO airportDTO) {
        Result result = validate(airportDTO);

        if (result.isOk()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Airport airport = modelMapper.map(airportDTO, Airport.class);
            airportRepository.save(airport);
        }
        return result;
    }

    private Result validate(AirportDTO airportDTO) {
        Result result = new Result().setStatus(ResultStatus.FAIL);

        if (airportDTO.getName() == null || airportDTO.getName().isEmpty()) {
            result.setMessage("Name is mandatory");
            result.setErrorCode("MISSING_NAME");
        }
        return result.getErrorCode() == null ? result.setStatus(ResultStatus.OK) : result;
    }

    @Override
    public ResultBean<List<Airport>> findAll() {
        ResultBean<List<Airport>> resultBean = new ResultBean<>();
        List<Airport> airportList = airportRepository.findAll();

        resultBean.setData(airportList);
        resultBean.setStatus(ResultStatus.OK);
        return resultBean;
    }

    @Override
    public ResultBean<Airport> findById(long airportId) {
        ResultBean<Airport> result = new ResultBean<>(ResultStatus.OK);
        Airport airport = airportRepository.findById(airportId);

        if (airport == null) {
            result.setStatus(ResultStatus.FAIL).setErrorCode("AIRPORT_NOT_FOUND");
            result.setMessage("Record not found with id : " + airportId);
        } else {
            result.setData(airport);
        }
        return result;
    }


    @Override
    public ResultBean<Airport> findByName(String name) {
        ResultBean<Airport> result = new ResultBean<>(ResultStatus.OK);
        Airport airport = airportRepository.findByName(name);

        if (airport == null) {
            result.setStatus(ResultStatus.FAIL).setErrorCode("AIRPORT_NOT_FOUND");
            result.setMessage("Record not found with name : " + name);
        } else {
            result.setData(airport);
        }
        return result;
    }

}
