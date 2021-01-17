package com.yusuf.finartz.service;

import com.yusuf.finartz.model.Ticket;

import java.util.List;

public interface TicketService {

    Ticket createTicket(Ticket ticket);

    Ticket updateTicket(Ticket ticket);

    Ticket cancelTicket(long ticketId);

    List<Ticket> getAllTickets();

    Ticket getTicketById(long ticketId);

    void deleteTicket(long ticketId);

    List<Ticket> searchTickets(Ticket ticket);

}
