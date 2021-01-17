package com.yusuf.finartz.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "routes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"fromAirportId","toAirportId"}, name = "uniqueRouteConstraint")})
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name ="fromAirportId")
    private long fromAirportId;

    @Column(name ="toAirportId")
    private long toAirportId;

    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;


}
