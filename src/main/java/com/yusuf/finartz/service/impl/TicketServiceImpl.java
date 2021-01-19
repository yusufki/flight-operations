package com.yusuf.finartz.service.impl;

import com.yusuf.finartz.bean.Result;
import com.yusuf.finartz.bean.ResultBean;
import com.yusuf.finartz.bean.ResultStatus;
import com.yusuf.finartz.exception.RecordNotCreateException;
import com.yusuf.finartz.exception.ResourceNotFoundException;
import com.yusuf.finartz.model.Flight;
import com.yusuf.finartz.model.Ticket;
import com.yusuf.finartz.model.TicketDTO;
import com.yusuf.finartz.repository.FlightRepository;
import com.yusuf.finartz.repository.TicketRepository;
import com.yusuf.finartz.service.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private static final String TICKET_CANCELLED = "C";
    private static final String TICKET_ACTIVE = "A";
    private static final String MASK_CHAR = "*";
    private static final int UNMASKED_CHAR_START = 4;
    private static final int UNMASKED_CHAR_END = 4;
    private static final int DEFAULT_CARD_NUMBER_LENGTH = 16;


    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FlightRepository flightRepository;


    @Override
    public ResultBean<List<Ticket>> findAll() {
        ResultBean<List<Ticket>> resultBean = new ResultBean<>();
        List<Ticket> ticketList = ticketRepository.findAll();

        resultBean.setData(ticketList);
        resultBean.setStatus(ResultStatus.OK);

        return resultBean;
    }

    @Override
    public ResultBean<Ticket> findById(long id) {
        ResultBean<Ticket> result = new ResultBean<>(ResultStatus.OK);
        Ticket ticket = ticketRepository.findById(id);

        if (ticket == null) {
            result.setStatus(ResultStatus.FAIL).setErrorCode("TICKET_NOT_FOUND");
            result.setMessage("Record not found with id : " + id);
        } else {
            result.setData(ticket);
        }

        return result;
    }


    @Override
    public Result createTicket(TicketDTO ticketDTO) {


        Result result = new Result().setStatus(ResultStatus.FAIL);
        Flight flight = flightRepository.findById(ticketDTO.getFlight().getId());
        if (flight == null) {
              result.setErrorCode("INVALID_FLIGHT");
              result.setMessage("Couldn't create the ticket, flight is not valid " + ticketDTO.getFlight());
              return result.setStatus(ResultStatus.FAIL);
        }

        result = validateTicketFields(ticketDTO,flight);
        if (result.isOk()) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
            ticket.setPaymentCardNumber(maskPaymentCardNumber(ticket.getPaymentCardNumber()));
            ticket.setStatus(TICKET_ACTIVE);
            flight.setSoldSeatCount(flight.getSoldSeatCount() + 1);
            flightRepository.save(flight);
            ticketRepository.save(ticket);
        }
        return result;
    }


    private Result validateTicketFields(TicketDTO ticketDTO,Flight flight) {
        Result result = new Result().setStatus(ResultStatus.FAIL);

        List<Ticket> ticketDb = ticketRepository.findByCustomerIdAndStatus(ticketDTO.getCustomerId(), TICKET_ACTIVE);
        if (!ticketDb.isEmpty()) {
            result.setErrorCode("INVALID_TICKET");
            result.setMessage("Couldn't create the ticket, the customer has already bought");
            return result;
        }

        if (flight.getSoldSeatCount() == flight.getSeatCapacity()) {
            result.setErrorCode("INVALID_TICKET");
            result.setMessage("Couldn't create the ticket, flight is full!");
            return result;
        }
        if (flight.getPrice() != ticketDTO.getPrice() || !flight.getCurrency().equals(ticketDTO.getCurrency())) {
            result.setErrorCode("INVALID_TICKET");
            result.setMessage("Couldn't create the ticket, check the price (" + ticketDTO.getPrice() + "  " + ticketDTO.getCurrency() +
                    " != " + flight.getPrice() + " " + flight.getCurrency() + ")" );
            return result;
        }
        if (flight.getFlightDate().isBefore(LocalDateTime.now())) {
            result.setErrorCode("INVALID_TICKET");
            result.setMessage("Couldn't create the ticket, flight is no longer valid :  " + flight.getFlightDate());
            return result;
        }

        if (!validateTicketCardNumber(ticketDTO.getPaymentCardNumber())) {
            result.setErrorCode("INVALID_TICKET");
            result.setMessage("Couldn't create the ticket, payment card number is not valid :  " + ticketDTO.getPaymentCardNumber());
            return result;
        }

        return result.getErrorCode() == null ? result.setStatus(ResultStatus.OK) : result;
    }

    private static String maskPaymentCardNumber(String paymentCardNumber) {
        String ccNumber = paymentCardNumber.replaceAll("\\D", "");

        StringBuilder sb = new StringBuilder();
        sb.append(ccNumber, 0, UNMASKED_CHAR_START);
        for (int i = 0; i < ccNumber.length() - (UNMASKED_CHAR_START + UNMASKED_CHAR_END); i++) {
            sb.append(MASK_CHAR);
        }
        sb.append(ccNumber, ((ccNumber.length() - UNMASKED_CHAR_END) ), (ccNumber.length()));
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
    public Ticket cancelTicket(long ticketId) {
        Ticket ticketDb = this.ticketRepository.findById(ticketId);

        if (ticketDb != null) {
            ticketDb.setStatus(TICKET_CANCELLED);

            Flight flight = ticketDb.getFlight();

            if (flight == null) {
                throw new RecordNotCreateException("Couldn't create the ticket, flight is not valid ");
            }

            flight.setSoldSeatCount(flight.getSoldSeatCount() - 1);

            ticketRepository.save(ticketDb);
            return ticketDb;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + ticketId);
        }
    }


    @Override
    public List<Ticket> searchTickets(Ticket ticket) {

        if (ticket.getCustomerId() > 0 && ticket.getFlight()!=null) {
            return this.ticketRepository.findByCustomerIdAndFlightId(ticket.getCustomerId(), ticket.getFlight().getId());
        } else if (ticket.getCustomerId() > 0) {
            return this.ticketRepository.findByCustomerId(ticket.getCustomerId());
        } else {
            return this.ticketRepository.findByFlightId(ticket.getFlight().getId());
        }
    }


    @Override
    public void deleteTicket(long ticketId) {
        Ticket ticketDb = this.ticketRepository.findById(ticketId);

        if (ticketDb != null) {
            this.ticketRepository.delete(ticketDb);
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + ticketId);
        }

    }
}
