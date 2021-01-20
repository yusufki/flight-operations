package com.yusuf.finartz.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FlightDTO {

    private long id;
    private Route route;
    private Airway airway;
    private LocalDateTime flightDate;
    private int seatCapacity;
    private int soldSeatCount;
    private float price;
    private String currency;

}
