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
    public ResponseEntity<ApiResponse<List<Ticket>>> getTickets() {
        ApiResponse<List<Ticket>> response = new ApiResponse<>(true, "Tickets retrieved successfully", HttpStatus.OK, tickets, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    // Insert Data
    @PostMapping("/")
    public ResponseEntity<ApiResponse<Ticket>> addTicket(@RequestBody Ticket ticket) {
        tickets.add(ticket);
        ApiResponse<Ticket> response = new ApiResponse<>(true, "Ticket added successfully", HttpStatus.CREATED, ticket, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    // Get By ID
    @GetMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> getTicketById(@PathVariable("ticket-id") Integer ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                ApiResponse<Ticket> response = new ApiResponse<>(true, "Ticket found", HttpStatus.OK, ticket, LocalDateTime.now());
                return ResponseEntity.ok(response);
            }
        }
        ApiResponse<Ticket> errorResponse = new ApiResponse<>(false, "Ticket not found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    // Update By ID
    @PutMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicketById(@PathVariable("ticket-id") Integer ticketId, @RequestBody TicketRequest ticketRequest) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                ticket.setPassengerName(ticketRequest.getPassengerName());
                ticket.setTravelDate(ticketRequest.getTravelDate());
                ticket.setSourceStation(ticketRequest.getSourceStation());
                ticket.setDestinationStation(ticketRequest.getDestinationStation());
                ticket.setPrice(ticketRequest.getPrice());
                ticket.setPaymentStatus(ticketRequest.isPaymentStatus());
                ticket.setTicketStatus(ticketRequest.getTicketStatus());
                ticket.setSeatNumber(ticketRequest.getSeatNumber());
                ApiResponse<Ticket> response = new ApiResponse<>(true, "Ticket updated successfully", HttpStatus.OK, ticket, LocalDateTime.now());
                return ResponseEntity.ok(response);
            }
        }
        ApiResponse<Ticket> errorResponse = new ApiResponse<>(false, "Ticket not found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    // Delete By ID
    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> deleteTicket(@PathVariable("ticket-id") Integer ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                tickets.remove(ticket);
                ApiResponse<Ticket> response = new ApiResponse<>(true, "Ticket deleted successfully", HttpStatus.OK, null, LocalDateTime.now());
                return ResponseEntity.ok(response);
            }
        }
        ApiResponse<Ticket> errorResponse = new ApiResponse<>(false, "Ticket not found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    // Search By Name
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Ticket>>> searchTicketByName(@RequestParam String passengerName) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getPassengerName().toLowerCase().contains(passengerName.toLowerCase())) {
                ticketList.add(ticket);
            }
        }
        if (ticketList.isEmpty()) {
            ApiResponse<List<Ticket>> errorResponse = new ApiResponse<>(false, "No tickets found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ApiResponse<List<Ticket>> response = new ApiResponse<>(true, "Tickets found", HttpStatus.OK, ticketList, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    // Filter By ticketStatus and travelDate
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Ticket>>> filterTicket(@RequestParam Status ticketStatus, @RequestParam String travelDate) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getTicketStatus() == ticketStatus && ticket.getTravelDate().equals(travelDate)) {
                ticketList.add(ticket);
            }
        }
        if (ticketList.isEmpty()) {
            ApiResponse<List<Ticket>> errorResponse = new ApiResponse<>(false, "No matching tickets found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ApiResponse<List<Ticket>> response = new ApiResponse<>(true, "Tickets filtered successfully", HttpStatus.OK, ticketList, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
