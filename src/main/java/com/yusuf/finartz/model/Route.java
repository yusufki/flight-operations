package com.yusuf.finartz.model;


import com.yusuf.finartz.bean.IdObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Route extends IdObject {

    @ManyToOne
    @JoinColumn(name = "FROM_AIRPORT_ID")
    private Airport fromAirport;

    @ManyToOne
    @JoinColumn(name = "TO_AIRPORT_ID")
    private Airport toAirport;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<Flight> flight;
}
