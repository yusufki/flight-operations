package com.yusuf.finartz.service;

import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Airway;
import com.yusuf.finartz.repository.AirwayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirwayServiceImpl implements AirwayService {

    @Autowired
    AirwayRepository airwayRepository;

    @Override
    public Airway createAirway(Airway airway) {
       /*
        try{
            return airwayRepository.save(airway);
        }catch (Exception e){
            throw new ResourceNotFoundException("Record already exists " + airway.getName());
        }
        */

       return   airwayRepository.save(airway);

    }

    @Override
    public Airway updateAirway(Airway airway) {
        Optional<Airway> airwayDb = this.airwayRepository.findById(airway.getId());

        if (airwayDb.isPresent()){
            Airway airwayUpdate = airwayDb.get();
            airwayUpdate.setId(airway.getId());
            airwayUpdate.setName(airway.getName());
            airwayRepository.save(airwayUpdate);
            return airwayUpdate;
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + airway.getId());
        }
    }

    @Override
    public List<Airway> getAllAirways() {
        return this.airwayRepository.findAll();
    }

    @Override
    public List<Airway> searchAirways(Airway airway) {
        return this.airwayRepository.findByName(airway.getName());
    }

    @Override
    public Airway getAirwayById(long airwayId) {
        Optional<Airway> airwayDb = this.airwayRepository.findById(airwayId);

        if (airwayDb.isPresent()){
            return airwayDb.get();
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + airwayId);
        }
    }


    @Override
    public void deleteAirway(long airwayId) {
        Optional<Airway> airwayDb = this.airwayRepository.findById(airwayId);

        if (airwayDb.isPresent()){
            this.airwayRepository.delete(airwayDb.get());
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + airwayId);
        }

    }
}
