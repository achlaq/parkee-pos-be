package com.parkee.pos_api_java.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Data
public class Ticket extends BaseEntity{

    @Column(name="plate_number", nullable=false, length=16)
    private String plateNumber;

    @Column(nullable=false)
    private Instant checkInAt;

    private Instant checkOutAt;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=10)
    private Status status;

    @Column(nullable=false)
    private boolean active = true;

    public enum Status { OPEN, CLOSED }
}

