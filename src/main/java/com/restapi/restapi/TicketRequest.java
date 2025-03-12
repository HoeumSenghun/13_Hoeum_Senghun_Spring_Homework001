package com.restapi.restapi;

import lombok.Data;

@Data
public class TicketRequest {
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private boolean paymentStatus;
    private Status ticketStatus;
    private String seatNumber;
}
