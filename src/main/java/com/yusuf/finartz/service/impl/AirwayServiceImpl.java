package com.yusuf.finartz.service.impl;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.bean.ResultStatus;
import com.yusuf.finartz.model.Airway;
import com.yusuf.finartz.model.AirwayDTO;
import com.yusuf.finartz.repository.AirwayRepository;
import com.yusuf.finartz.service.AirwayService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AirwayServiceImpl implements AirwayService {

    @Autowired
    AirwayRepository airwayRepository;


    @Override
    public ResultBean<List<Airway>> findAll() {
        ResultBean<List<Airway>> resultBean = new ResultBean<>();
        List<Airway> airwayList = airwayRepository.findAll();

        resultBean.setData(airwayList);
        resultBean.setStatus(ResultStatus.OK);

        return resultBean;
    }


    @Override
    public ResultBean<Airway> findById(long airwayId) {
        ResultBean<Airway> result = new ResultBean<>(ResultStatus.OK);
        Airway airway = airwayRepository.findById(airwayId);

        if (airway == null) {
            result.setStatus(ResultStatus.FAIL).setErrorCode("AIRWAY_NOT_FOUND");
            result.setMessage("Record not found with id : " + airwayId);
        } else {
            result.setData(airway);
        }

        return result;
    }


    @Override
    public ResultBean<Airway> findByName(String name) {
        ResultBean<Airway> result = new ResultBean<>(ResultStatus.OK);
        Airway airway = airwayRepository.findByName(name);

        if (airway == null) {
            result.setStatus(ResultStatus.FAIL).setErrorCode("AIRWAY_NOT_FOUND");
            result.setMessage("Record not found with name : " + name);
        } else {
            result.setData(airway);
        }

        return result;
    }

    @Override
    public Result createAirway(AirwayDTO airwayDTO) {
        Result result = validate(airwayDTO);

        if (result.isOk()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Airway airway = modelMapper.map(airwayDTO, Airway.class);
            airwayRepository.save(airway);
        }
        return result;

    }

    private Result validate(AirwayDTO airwayDTO) {
        Result result = new Result().setStatus(ResultStatus.FAIL);

        if (airwayDTO.getName() == null) {
            result.setErrorCode("MISSING_NAME");
        }

        return result.getErrorCode() == null ? result.setStatus(ResultStatus.OK) : result;
    }
}
