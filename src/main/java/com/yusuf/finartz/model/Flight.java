package com.yusuf.finartz.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "flights",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"routeId","airwayId","flightDate"}, name = "uniqueFlightConstraint")})

@Getter
@Setter
public class Flight {

    private static final float PRICE_CAPACITY_INCREMENT_RANGE = 10f; // Each 10 percent capacity increment
    private static final float PRICE_INCREMENT_RATE = 10f; //Price will be increment 10 percent
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name ="routeId")
    private long routeId;

    @Column(name ="airwayId")
    private long airwayId;

    @Column(name ="flightDate")
    private LocalDateTime flightDate;

    @Column(name ="seatCapacity")
    private int seatCapacity;

    @Column(name ="soldSeatCount")
    private int soldSeatCount;

    @Column(name ="price")
    private float price;

    @Column(name ="currency")
    private String currency;

    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;
    

    public float getPrice() {
        return calculatePrice();
    }

    private float calculatePrice(){
        float calculatedPrice = this.price;
        float flightCapacityRate = ( (float) soldSeatCount / (float) seatCapacity ) * 100; // capacity rate should return like 18
        int flightCapacityRange = (int) (flightCapacityRate /  PRICE_CAPACITY_INCREMENT_RANGE);
        for (int i = 0; i< flightCapacityRange; i++){
            calculatedPrice = calculatedPrice + (calculatedPrice * PRICE_INCREMENT_RATE/100);
        }
        return calculatedPrice;
    }
}
