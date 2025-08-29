package com.parkee.pos_api_java.service;

import com.parkee.pos_api_java.dto.TicketRespondDTO;
import com.parkee.pos_api_java.dto.VoucherDTO;
import com.parkee.pos_api_java.entity.Ticket;
import com.parkee.pos_api_java.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final VoucherService voucherService;
    private final TicketRepository ticketRepository;

    private static final BigDecimal SECONDS_PER_HOUR = BigDecimal.valueOf(3600);
    private static final BigDecimal RATE_PER_HOUR = BigDecimal.valueOf(3000);

    private final Clock clock = Clock.system(ZoneId.of("Asia/Jakarta"));


    @Transactional
    public TicketRespondDTO checkIn(String plateNumber) {
        String plate = plateNumber.trim().toUpperCase(Locale.ROOT);

        Optional<Ticket> existing = ticketRepository.lockActiveByPlate(plate);

        if (existing.isPresent()) {
            return TicketRespondDTO.from(existing.get(), true);
        }

        Ticket t = new Ticket();
        t.setPlateNumber(plate);
        t.setCheckInAt(Instant.now(clock));
        t.setStatus(Ticket.Status.OPEN);
        t.setActive(true);

        t.setCreatedByUser("system temp");
        t.setUpdatedByUser("system temp");

        Ticket saved = ticketRepository.save(t);
        return TicketRespondDTO.from(saved, false);
    }

    @Transactional
    public TicketRespondDTO previewCheckout(String plateNumber, String voucherId) {
        String plate = plateNumber.trim().toUpperCase(Locale.ROOT);
        BigDecimal discount;

        Optional<Ticket> opt = ticketRepository.findByPlateNumberAndActiveTrue(plate);
        if (opt.isEmpty()) {
            return TicketRespondDTO.invalid("Tidak ada tiket aktif untuk plat " + plate);
        }

        Ticket t = opt.get();

        Instant now = Instant.now(clock);
        LocalDateTime checkIn = LocalDateTime.ofInstant(t.getCheckInAt(), clock.getZone());
        LocalDateTime checkOutPreview = LocalDateTime.ofInstant(now, clock.getZone());

        if (checkOutPreview.isBefore(checkIn)) {
            return TicketRespondDTO.invalid("Waktu sistem tidak valid (checkout < checkin)");
        }

        VoucherDTO v = voucherService.findValidVoucher(voucherId);
        discount = (v == null || v.getDiscount() == null) ? BigDecimal.ZERO : v.getDiscount();


        BigDecimal total = calculateParkingPrice(checkIn, checkOutPreview, voucherId);
        long durationMinutes = Duration.between(t.getCheckInAt(), now).toMinutes();

        TicketRespondDTO dto = TicketRespondDTO.from(t, false);
        dto.setPreview(true);
        dto.setDurationMinutes(durationMinutes);
        dto.setTotalPrice(total);
        dto.setDiscount(discount);
        dto.setMessage("Preview checkout berhasil");
        return dto;
    }

    @Transactional
    public TicketRespondDTO finalizeCheckout(String plateNumber, String voucherId) {

        String plate = plateNumber.trim().toUpperCase(Locale.ROOT);

        Optional<Ticket> opt = ticketRepository.lockActiveByPlate(plate);
        if (opt.isEmpty()) {
            return TicketRespondDTO.invalid("Tidak ada tiket aktif untuk plat " + plate);
        }

        Ticket t = opt.get();

        Instant now = Instant.now(clock);
        LocalDateTime checkIn = LocalDateTime.ofInstant(t.getCheckInAt(), clock.getZone());
        LocalDateTime checkOut = LocalDateTime.ofInstant(now, clock.getZone());

        if (checkOut.isBefore(checkIn)) {
            return TicketRespondDTO.invalid("Waktu sistem tidak valid (checkout < checkin)");
        }

        BigDecimal total = calculateParkingPrice(checkIn, checkOut, voucherId);

        t.setCheckOutAt(now);
        t.setTotalPrice(total);
        t.setStatus(Ticket.Status.CLOSED);
        t.setActive(false);

        Ticket saved = ticketRepository.save(t);

        TicketRespondDTO dto = TicketRespondDTO.from(saved, false);
        dto.setPreview(false);
        dto.setDurationMinutes(Duration.between(t.getCheckInAt(), now).toMinutes());
        dto.setMessage("Checkout berhasil");
        return dto;
    }

    public BigDecimal calculateParkingPrice(
            LocalDateTime checkInDate,
            LocalDateTime checkOutDate,
            String voucherId
    ) {
        BigDecimal discount;

        if (!checkOutDate.isAfter(checkInDate)) {
            return BigDecimal.ZERO;
        }

        long seconds = Duration.between(checkInDate, checkOutDate).getSeconds();
        BigDecimal durationSeconds = BigDecimal.valueOf(seconds);
        int hours = durationSeconds
                .divide(SECONDS_PER_HOUR, 0, RoundingMode.CEILING)
                .intValue();

        BigDecimal price = BigDecimal.valueOf(hours).multiply(RATE_PER_HOUR);

        VoucherDTO v = voucherService.findValidVoucher(voucherId);
        discount = (v == null || v.getDiscount() == null) ? BigDecimal.ZERO : v.getDiscount();

        return price.subtract(discount).max(BigDecimal.ZERO).setScale(0, RoundingMode.DOWN);
    }
}