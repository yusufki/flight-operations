package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Airport;
import com.yusuf.finartz.model.Airway;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirwayRepository extends JpaRepository<Airway,Long> {
    List<Airway> findByName(String name);

}
