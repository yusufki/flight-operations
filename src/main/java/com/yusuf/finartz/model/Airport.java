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
public class Airport extends IdObject {

    private String name;

    @OneToMany(mappedBy = "fromAirport", fetch = FetchType.LAZY)
    private List<Route> routeListFrom;

    @OneToMany(mappedBy = "toAirport", fetch = FetchType.LAZY)
    private List<Route> routeListTo;

}
