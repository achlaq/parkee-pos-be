package com.parkee.pos_api_java.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(
    name = "member",
    indexes = {
            @Index(name = "idx_members_plate_number", columnList = "plate_number")
    }
)
@Data
public class Member extends BaseEntity {

    @NotBlank
    @Size(max = 150)
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(name = "plate_number", nullable = false, length = 15, unique = true)
    private String plateNumber;

    @Column(name = "member_expired_date")
    private LocalDateTime memberExpiredDate;
}
