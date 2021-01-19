package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByName(String name);

    Airport findById(long id);

}
