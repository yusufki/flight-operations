package com.yusuf.finartz.service;

import com.yusuf.finartz.model.Airway;

import java.util.List;

public interface AirwayService {

    Airway createAirway(Airway airway);

    Airway updateAirway(Airway airway);

    List<Airway> getAllAirways();

    Airway getAirwayById(long airwayId);

    void deleteAirway(long airwayId);

    List<Airway> searchAirways(Airway airway);

}
