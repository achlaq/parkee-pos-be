package com.parkee.pos_api_java.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketRequestDTO {
    private String plateNumber;
    private String voucherId;
}
