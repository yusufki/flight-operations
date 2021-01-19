package com.yusuf.finartz.controller;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Ticket;
import com.yusuf.finartz.model.TicketDTO;
import com.yusuf.finartz.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/findAll")
    public ResultBean<List<Ticket>> findAll() {
        return ticketService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ResultBean<Ticket> findById(@PathVariable long id) {
        return ticketService.findById(id);
    }

    @PostMapping("/create")
    public Result createFlight(@RequestBody TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }


    @PostMapping("/search")
    public ResponseEntity searchAirports(@RequestBody Ticket ticket) {
        return ResponseEntity.ok().body(ticketService.searchTickets(ticket));
    }


    @PutMapping("/cancel/{ticketId}")
    public ResponseEntity cancelTicket(@PathVariable long ticketId) {
        try {
            return ResponseEntity.ok().body(ticketService.cancelTicket(ticketId));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
