package com.yusuf.finartz.service;

import antlr.StringUtils;
import com.yusuf.finartz.exception.RecordNotCreateException;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Airway;
import com.yusuf.finartz.model.Flight;
import com.yusuf.finartz.model.Ticket;
import com.yusuf.finartz.model.Route;
import com.yusuf.finartz.repository.AirwayRepository;
import com.yusuf.finartz.repository.FlightRepository;
import com.yusuf.finartz.repository.TicketRepository;
import com.yusuf.finartz.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private static final String TICKET_CANCELLED = "C";
    private static final String TICKET_ACTIVE = "A";
    private static final String  MASK_CHAR = "*";
    private static final int UNMASKED_CHAR_START = 4;
    private static final int UNMASKED_CHAR_END = 4;
    private static final int DEFAULT_CARD_NUMBER_LENGTH = 16 ;


    @Autowired
    private
    TicketRepository ticketRepository;

    @Autowired
    private
    FlightRepository flightRepository;


    @Override
    public Ticket createTicket(Ticket ticket) {
       /*
        try{
            return ticketRepository.save(ticket);
        }catch (Exception e){
            throw new ResourceNotFoundException("Record already exists " + ticket.getName());
        }
        */
        Optional<Flight> flight  = this.flightRepository.findById(ticket.getFlightId());
        if (!flight.isPresent()){
            throw new RecordNotCreateException("Couldn't create the ticket, flight is not valid " + ticket.getFlightId() );
        }
        Flight flightDb = flight.get();

       if (validateTicketFields(ticket,flightDb)) {

           flightDb.setSoldSeatCount(flightDb.getSoldSeatCount()+1);
           ticket.setPaymentCardNumber(maskPaymentCardNumber(ticket.getPaymentCardNumber()));
           ticket.setStatus(TICKET_ACTIVE);
           return ticketRepository.save(ticket);
       }else {
           throw new RecordNotCreateException("Couldn't create the ticket");
       }
    }



    private boolean validateTicketFields(Ticket ticket,Flight flightDb) {

        List<Ticket> ticketDb  = this.ticketRepository.findByCustomerIdAndStatus(ticket.getCustomerId(),TICKET_ACTIVE);
        if (!ticketDb.isEmpty()){
            throw new RecordNotCreateException("Couldn't create the ticket, the customer has already bought");
        }

        if (flightDb.getSoldSeatCount() == flightDb.getSeatCapacity()){
            throw new RecordNotCreateException("Couldn't create the ticket, flight is full!");
        }
        if (flightDb.getPrice() == ticket.getPrice() && !flightDb.getCurrency().equals(ticket.getCurrency()))
        {
            throw new RecordNotCreateException("Couldn't create the ticket, check the price " + ticket.getPrice() + " " + ticket.getCurrency()+
                    " " + flightDb.getPrice() + " " + flightDb.getCurrency());
        }
        if (flightDb.getFlightDate().isBefore(LocalDateTime.now())){
            throw new RecordNotCreateException("Couldn't create the ticket, flight is no longer valid :  " + flightDb.getFlightDate() );
        }

        if (!validateTicketCardNumber(ticket.getPaymentCardNumber())){
            throw new RecordNotCreateException("Couldn't create the ticket, payment card number is not valid :  " + ticket.getPaymentCardNumber());
        }
        return  true;
    }
    private static String maskPaymentCardNumber(String paymentCardNumber) {
        String ccNumber = paymentCardNumber.replaceAll("\\D", "");

        StringBuilder sb = new StringBuilder();
        sb.append(ccNumber, 0, UNMASKED_CHAR_START);
        for (int i = 0; i<ccNumber.length()-(UNMASKED_CHAR_START + UNMASKED_CHAR_END);i++ ){
            sb.append(MASK_CHAR);
        }
        sb.append(ccNumber, ((ccNumber.length()- UNMASKED_CHAR_END)-1), (ccNumber.length()- 1));
        return sb.toString();
    }

    private boolean validateTicketCardNumber(String paymentCardNumber) {
   /*
        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
                "(?<mastercard>5[1-5][0-9]{14})|" +
                "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                "(?<amex>3[47][0-9]{13})|" +
                "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

        Pattern pattern = Pattern.compile(regex);

        String ccNumber = paymentCardNumber.replaceAll("\\D", "");

        //Match the card
        Matcher matcher = pattern.matcher(ccNumber);

        System.out.println(matcher.matches());

        return(matcher.matches()) ;
        */

        String ccNumber = paymentCardNumber.replaceAll("\\D", "");
        return ccNumber.length() == DEFAULT_CARD_NUMBER_LENGTH;

    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        Optional<Ticket> ticketDb = this.ticketRepository.findById(ticket.getId());

        if (ticketDb.isPresent()){
            Ticket ticketUpdate = ticketDb.get();
            ticketUpdate.setId(ticket.getId());
            ticketRepository.save(ticketUpdate);
            return ticketUpdate;
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + ticket.getId());
        }
    }

    @Override
    public Ticket cancelTicket(long ticketId) {
        Optional<Ticket> ticketDb = this.ticketRepository.findById(ticketId);

        if (ticketDb.isPresent()){
            Ticket ticketUpdate = ticketDb.get();
            ticketUpdate.setStatus(TICKET_CANCELLED);

            Optional<Flight> flight  = this.flightRepository.findById(ticketUpdate.getFlightId());
            if (!flight.isPresent()){
                throw new RecordNotCreateException("Couldn't create the ticket, flight is not valid " + ticketUpdate.getFlightId() );
            }
            Flight flightDb = flight.get();
            flightDb.setSoldSeatCount(flightDb.getSoldSeatCount()-1);

            ticketRepository.save(ticketUpdate);
            return ticketUpdate;
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + ticketId);
        }
    }

    @Override
    public List<Ticket> getAllTickets() {
        return this.ticketRepository.findAll();
    }

    @Override
    public List<Ticket> searchTickets(Ticket ticket) {

        if (ticket.getCustomerId() > 0 && ticket.getFlightId() >0 ) {
            return this.ticketRepository.findByCustomerIdAndFlightId(ticket.getCustomerId(),ticket.getFlightId());
        }
        else if (ticket.getCustomerId() > 0){
            return this.ticketRepository.findByCustomerId(ticket.getCustomerId());
        }else{
            return this.ticketRepository.findByFlightId(ticket.getFlightId());
        }
    }

    @Override
    public Ticket getTicketById(long ticketId) {
        Optional<Ticket> ticketDb = this.ticketRepository.findById(ticketId);

        if (ticketDb.isPresent()){
            return ticketDb.get();
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + ticketId);
        }
    }


    @Override
    public void deleteTicket(long ticketId) {
        Optional<Ticket> ticketDb = this.ticketRepository.findById(ticketId);

        if (ticketDb.isPresent()){
            this.ticketRepository.delete(ticketDb.get());
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + ticketId);
        }

    }
}
