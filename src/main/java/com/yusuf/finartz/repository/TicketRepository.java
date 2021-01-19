package com.yusuf.finartz.repository;

import com.yusuf.finartz.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findById(long id);

    List<Ticket> findByFlightId(long flightId);

    List<Ticket> findByCustomerIdAndFlightId(long customerId, long flightId);

    List<Ticket> findByCustomerId(long customerId);

    List<Ticket> findByCustomerIdAndStatus(long customerId, String status);


}
