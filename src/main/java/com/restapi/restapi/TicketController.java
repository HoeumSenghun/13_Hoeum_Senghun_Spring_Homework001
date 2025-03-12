package com.restapi.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets/")
public class TicketController {
    ArrayList<Ticket> tickets = new ArrayList<>();
    public TicketController() {
        tickets.add(new Ticket(1,"Senghun","12-03-2025","PP","BTB",12.5,true,Status.BOOKED,"A01"));
        tickets.add(new Ticket(2,"Mengsea","12-03-2025","PP","BTB",12.5,false,Status.CANCELLED,"A02"));
        tickets.add(new Ticket(3,"Seng Nguon","12-03-2025","PP","BTB",12.5,true,Status.COMPLETED,"A03"));
        tickets.add(new Ticket(4,"Sevthong","12-03-2025","PP","BTB",12.5,false,Status.BOOKED,"A04"));
    }
    // Get All Data
    @GetMapping("/")
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
    // Insert Data
    @PostMapping("/")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }
    // Get By ID
    @GetMapping("/{ticket-id}")
    public Ticket getTicketById(@PathVariable("ticket-id") Integer ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                return ticket;
            }
        }
        return null;
    }
    // Update By ID
    @PutMapping("/{ticket-id}")
    public Ticket updateTicketById(@PathVariable("ticket-id") Integer ticketId, @RequestBody TicketRequest ticketRequest) {
        for (Ticket ticket1 : tickets) {
            if (ticket1.getTicketId() == ticketId){
                ticket1.setPassengerName(ticketRequest.getPassengerName());
                ticket1.setTravelDate(ticketRequest.getTravelDate());
                ticket1.setSourceStation(ticketRequest.getSourceStation());
                ticket1.setDestinationStation(ticketRequest.getDestinationStation());
                ticket1.setPrice(ticketRequest.getPrice());
                ticket1.setPaymentStatus(ticketRequest.isPaymentStatus());
                ticket1.setTicketStatus(ticketRequest.getTicketStatus());
                ticket1.setSeatNumber(ticketRequest.getSeatNumber());
                return ticket1;
            }
        }
        return null;
    }

    // Search By Name
    @GetMapping("/search")
    public List<Ticket> searchTicketByName(@RequestParam String passengerName) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getPassengerName().toLowerCase().contains(passengerName.toLowerCase())) {
                ticketList.add(ticket);
            }
        }
        return ticketList;
    }
    // Filter By ticketStatus and travelDate
    @GetMapping("/filter")
    public List<Ticket> filterTicket(@RequestParam Status ticketStatus, @RequestParam String travelDate) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getTicketStatus() == ticketStatus && ticket.getTravelDate().equals(travelDate)) {
                ticketList.add(ticket);
            }
        }
        return ticketList;
    }
}
