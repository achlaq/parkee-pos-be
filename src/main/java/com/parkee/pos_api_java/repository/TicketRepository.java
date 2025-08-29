package com.parkee.pos_api_java.repository;

import com.parkee.pos_api_java.entity.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Optional<Ticket> findByPlateNumberAndActiveTrue(String plateNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Ticket t where t.plateNumber = :plate and t.active = true")
    Optional<Ticket> lockActiveByPlate(@Param("plate") String plate);
}
