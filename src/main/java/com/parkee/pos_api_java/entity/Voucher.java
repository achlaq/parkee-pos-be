package com.parkee.pos_api_java.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "voucher")
@Data
public class Voucher extends BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}