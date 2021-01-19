package com.yusuf.finartz.service;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.model.Ticket;
import com.yusuf.finartz.model.TicketDTO;

import java.util.List;

public interface TicketService {
    ResultBean<List<Ticket>> findAll();

    ResultBean<Ticket> findById(long id);

    Result createTicket(TicketDTO ticketDTO);


    Ticket cancelTicket(long ticketId);

    void deleteTicket(long ticketId);

    List<Ticket> searchTickets(Ticket ticket);

}
