package com.yusuf.finartz.model;

import com.yusuf.finartz.bean.IdObject;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity


@Getter
@Setter
@ToString

public class Ticket extends IdObject {

    @ManyToOne
    @JoinColumn(name = "FLIGHT_ID")
    private Flight flight;

    @Column(name="CUSTOMER_ID")
    private long customerId;

    @Column(name="PRICE")
    private float price;

    @Column(name="CURRENCY")
    private String currency;

    @Column(name="PAYMENT_CARD_NUMBER")
    private String paymentCardNumber;

    @Column(name="STATUS")
    private String status;

}
