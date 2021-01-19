package com.yusuf.finartz.service.impl;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.bean.ResultStatus;
import com.yusuf.finartz.model.Flight;
import com.yusuf.finartz.model.FlightDTO;
import com.yusuf.finartz.repository.AirwayRepository;
import com.yusuf.finartz.repository.FlightRepository;
import com.yusuf.finartz.repository.RouteRepository;
import com.yusuf.finartz.service.FlightService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private static final int MAX_SEAT_CAPACITY = 1000;
    private static final String BASE_CURRENCY_CODE = "TL";
    private static final String BASE_FOREIGN_CODE = "USD";

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirwayRepository airwayRepository;

    @Autowired
    RouteRepository routeRepository;


    @Override
    public ResultBean<List<Flight>> findAll() {
        ResultBean<List<Flight>> resultBean = new ResultBean<>();
        List<Flight> flightList = flightRepository.findAll();

        resultBean.setData(flightList);
        resultBean.setStatus(ResultStatus.OK);

        return resultBean;
    }

    @Override
    public ResultBean<Flight> findById(long id) {
        ResultBean<Flight> result = new ResultBean<>(ResultStatus.OK);
        Flight flight = flightRepository.findById(id);

        if (flight == null) {
            result.setStatus(ResultStatus.FAIL).setErrorCode("FLIGHT_NOT_FOUND");
            result.setMessage("Record not found with id : " + id);
        } else {
            result.setData(flight);
        }

        return result;
    }


    @Override
    public Result createFlight(FlightDTO flightDTO) {
        Result result = validateFlightFields(flightDTO);

        if (result.isOk()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Flight flight = modelMapper.map(flightDTO, Flight.class);
            flightRepository.save(flight);
        }
        return result;

    }

    private Result validateFlightFields(FlightDTO flightDTO) {
        Result result = new Result().setStatus(ResultStatus.FAIL);

        if (flightDTO.getPrice() <= 0) {
            result.setErrorCode("INVALID_PRICE");
            result.setMessage("Couldn't create the flight with price " + flightDTO.getPrice());
        } else if (!flightDTO.getCurrency().equals(BASE_CURRENCY_CODE) && !flightDTO.getCurrency().equals(BASE_FOREIGN_CODE)) {
            result.setErrorCode("INVALID_CURRENCY");
            result.setMessage("Couldn't create the flight with currency " + flightDTO.getCurrency());
        } else if (flightDTO.getSeatCapacity() <= 0 || flightDTO.getSeatCapacity() > MAX_SEAT_CAPACITY) {
            result.setErrorCode("INVALID_CAPACITY");
            result.setMessage("Couldn't create the flight with searCapacity " + flightDTO.getSeatCapacity());
        } else if (flightDTO.getFlightDate().isBefore(LocalDateTime.now())) {
            result.setErrorCode("INVALID_DATE");
            result.setMessage("Couldn't create the flight, for date :  " + flightDTO.getFlightDate());
        } else if (routeRepository.findById(flightDTO.getRoute().getId()) == null) {
            result.setErrorCode("INVALID_ROUTE");
            result.setMessage("Couldn't create the flight, route is not valid " + flightDTO.getRoute());
        } else if (airwayRepository.findById(flightDTO.getAirway().getId()) == null) {
            result.setErrorCode("INVALID_AIRWAY");
            result.setMessage("Couldn't create the flight, airway is not valid " + flightDTO.getAirway());
        }

        return result.getErrorCode() == null ? result.setStatus(ResultStatus.OK) : result;
    }

    @Override
    public ResultBean<List<Flight>> searchFlights(FlightDTO flightDTO) {
        ResultBean<List<Flight>> resultBean = new ResultBean<>();
        List<Flight> flightList;

        if (flightDTO.getRoute().getId() > 0 && flightDTO.getAirway().getId() > 0 && flightDTO.getFlightDate() != null) {
            flightList = flightRepository.findByRouteIdAndAirwayIdAndFlightDate(flightDTO.getRoute().getId(), flightDTO.getAirway().getId(), flightDTO.getFlightDate());
        }
        if (flightDTO.getRoute().getId() > 0 && flightDTO.getAirway().getId() > 0) {
            flightList = flightRepository.findByRouteIdAndAirwayId(flightDTO.getRoute().getId(), flightDTO.getAirway().getId());
        } else {
            flightList = flightRepository.findByRouteId(flightDTO.getRoute().getId());
        }

        resultBean.setData(flightList);
        resultBean.setStatus(ResultStatus.OK);

        return resultBean;

    }

}
