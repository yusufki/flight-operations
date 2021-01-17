package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {


    List<Ticket> findByFlightId(long flightId);
  //  List<Ticket> findByRouteIdAndAirwayId(long routeId, long airwayId);
   // List<Ticket> findByRouteIdAndAirwayIdAndTicketDate(long routeId, long airwayId, LocalDateTime ticketDate);

}
