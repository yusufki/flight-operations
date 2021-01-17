package com.yusuf.finartz.controller;

import com.yusuf.finartz.exception.RecordNotCreateException;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Ticket;
import com.yusuf.finartz.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return ResponseEntity.ok().body(ticketService.getAllTickets());
    }

    @PostMapping("/tickets/search")
    public ResponseEntity searchAirports(@RequestBody Ticket ticket){
        return ResponseEntity.ok().body(ticketService.searchTickets(ticket));
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity getTicketById(@PathVariable long id){
        try {
            return ResponseEntity.ok().body(ticketService.getTicketById(id));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/tickets")
    public ResponseEntity createTicket(@RequestBody Ticket ticket){
        try {
            return ResponseEntity.ok().body(ticketService.createTicket(ticket));
        }
        catch(RecordNotCreateException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity updateTicket(@PathVariable long id, @RequestBody Ticket ticket){
        ticket.setId(id);
        try{
            return ResponseEntity.ok().body(ticketService.updateTicket(ticket));
        }
                catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @PutMapping("/tickets/cancel/{ticketId}")
    public ResponseEntity cancelTicket(@PathVariable long ticketId){
        try{
            return ResponseEntity.ok().body(ticketService.cancelTicket(ticketId));
        }
        catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity deleteTicket(@PathVariable long id){
        try{
            ticketService.deleteTicket(id);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }
}
