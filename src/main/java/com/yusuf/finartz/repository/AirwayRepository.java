package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Airway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirwayRepository extends JpaRepository<Airway, Long> {
    Airway findByName(String name);

    Airway findById(long id);

}
