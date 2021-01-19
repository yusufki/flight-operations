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
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"FROM_AIRPORT_ID","TO_AIRPORT_ID"})
})
public class Route extends IdObject {

    @ManyToOne
    @JoinColumn(name = "FROM_AIRPORT_ID")
    private Airport fromAirport;

    @ManyToOne
    @JoinColumn(name = "TO_AIRPORT_ID")
    private Airport toAirport;


}
