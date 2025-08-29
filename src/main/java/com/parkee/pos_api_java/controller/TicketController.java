package com.parkee.pos_api_java.controller;

import com.parkee.pos_api_java.dto.TicketRequestDTO;
import com.parkee.pos_api_java.dto.TicketRespondDTO;
import com.parkee.pos_api_java.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/check-in")
    public ResponseEntity<Object> checkIn(@RequestBody TicketRequestDTO dto) {
        try {
            TicketRespondDTO ticketRespondDto = ticketService.checkIn(dto.getPlateNumber());
            return ResponseEntity.ok(ticketRespondDto);
        } catch (RuntimeException e) {
            log.error("#TicketController#checkIn ERROR! with plateNumber: {} and error: {}", dto.getPlateNumber(),
                    e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @GetMapping("/preview-check-out")
    public ResponseEntity<Object> previewCheckout(@RequestParam String plateNumber,
                                                  @RequestParam(required = false) String voucherId) {
        try {
            TicketRespondDTO ticketRespondDto = ticketService.previewCheckout(plateNumber, voucherId);
            return ResponseEntity.ok(ticketRespondDto);
        } catch (RuntimeException e) {
            log.error("#TicketController#previewCheckout ERROR! with plateNumber: {} and error: {}", plateNumber,
                    e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @PostMapping("/check-out")
    public ResponseEntity<Object> checkout(@RequestBody TicketRequestDTO dto) {
        try {
            TicketRespondDTO ticketRespondDto = ticketService.finalizeCheckout(dto.getPlateNumber(), dto.getVoucherId());
            return ResponseEntity.ok(ticketRespondDto);
        } catch (RuntimeException e) {
            log.error("#TicketController#checkout ERROR! with plateNumber: {} and error: {}", dto.getPlateNumber(),
                    e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
