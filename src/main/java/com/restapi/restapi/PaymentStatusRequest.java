package com.restapi.restapi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentStatusRequest {
    private List<Integer> ticketIds;
    private boolean paymentStatus;
}
