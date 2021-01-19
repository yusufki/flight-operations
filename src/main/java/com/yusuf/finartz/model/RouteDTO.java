package com.yusuf.finartz.model;


import com.yusuf.finartz.bean.IdObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RouteDTO {

    private long id;
    private Airport fromAirport;
    private Airport toAirport;
    private List<Flight> flight;
}
