package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    Route findById(long id);

    List<Route> findByToAirportId(long fromAirportId);

    List<Route> findByFromAirportId(long fromAirportId);

    List<Route> findByFromAirportIdAndToAirportId(long fromAirportId, long toAirPortId);


}
