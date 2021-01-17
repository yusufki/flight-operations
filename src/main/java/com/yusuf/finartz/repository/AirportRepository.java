package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport,Long> {
    List<Airport> findByName(String name);

}
