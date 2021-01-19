package com.yusuf.finartz.model;


import com.yusuf.finartz.bean.IdObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"ROUTE_ID","AIRWAY_ID","FLIGHT_DATE"})
})
public class Flight extends IdObject {

    private static final float PRICE_CAPACITY_INCREMENT_RANGE = 10f; // Each 10 percent capacity increment
    private static final float PRICE_INCREMENT_RATE = 10f; //Price will be increment 10 percent

    @ManyToOne
    @JoinColumn(name = "ROUTE_ID")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "AIRWAY_ID")
    private Airway airway;

    @Column(name = "FLIGHT_DATE")
    private LocalDateTime flightDate;

    @Column(name = "SEAT_CAPACITY")
    private int seatCapacity;

    private int soldSeatCount;

    private float price;

    private String currency;

    public float getPrice() {
        return calculatePrice();
    }

    private float calculatePrice() {
        float calculatedPrice = this.price;
        float flightCapacityRate = ((float) soldSeatCount / (float) seatCapacity) * 100; // capacity rate should return like 18
        int flightCapacityRange = (int) (flightCapacityRate / PRICE_CAPACITY_INCREMENT_RANGE);
        for (int i = 0; i < flightCapacityRange; i++) {
            calculatedPrice = calculatedPrice + (calculatedPrice * PRICE_INCREMENT_RATE / 100);
        }
        return calculatedPrice;
    }
}
