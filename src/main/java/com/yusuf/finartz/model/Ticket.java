package com.yusuf.finartz.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"flightId","customerId"}, name = "uniqueTicketConstraint")})

@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name ="flightId")
    private long flightId;

    @Column(name ="customerId")
    private long customerId;

    @Column(name ="price")
    private float price;

    @Column(name ="currency")
    private String currency;

    @Column(name ="paymentCardNumber")
    private String paymentCardNumber;

    @Column(name ="status")
    private String status;

    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;

}
