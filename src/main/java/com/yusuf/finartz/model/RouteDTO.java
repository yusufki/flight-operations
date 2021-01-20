package com.yusuf.finartz.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class RouteDTO {

    private long id;
    private Airport fromAirport;
    private Airport toAirport;
}
