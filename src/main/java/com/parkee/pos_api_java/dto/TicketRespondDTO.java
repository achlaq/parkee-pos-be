package com.parkee.pos_api_java.dto;

import com.parkee.pos_api_java.entity.Ticket;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRespondDTO {
    private String id;
    private String plateNumber;
    private Instant checkInAt;
    private Instant checkOutAt;
    private long durationMinutes;
    private BigDecimal discount;
    private BigDecimal totalPrice;
    private String status;
    private boolean preview;
    private boolean alreadyInside;
    private boolean invalid;
    private String message;

    public static TicketRespondDTO from(Ticket t, boolean alreadyInside) {
        String msg = alreadyInside
                ? "Kendaraan sudah berada di area. Menggunakan tiket aktif yang sama."
                : "Check-in berhasil.";
        return TicketRespondDTO.builder()
                .id(t.getId())
                .plateNumber(t.getPlateNumber())
                .checkInAt(t.getCheckInAt())
                .checkOutAt(t.getCheckOutAt())
                .totalPrice(t.getTotalPrice())
                .status(t.getStatus().name())
                .alreadyInside(alreadyInside)
                .invalid(false)
                .message(msg)
                .build();
    }

    public static TicketRespondDTO invalid(String msg) {
        return TicketRespondDTO.builder()
                .invalid(true)
                .message(msg)
                .build();
    }

}
