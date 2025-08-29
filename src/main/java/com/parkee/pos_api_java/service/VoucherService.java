package com.parkee.pos_api_java.service;

import com.parkee.pos_api_java.dto.VoucherDTO;
import com.parkee.pos_api_java.entity.Voucher;
import com.parkee.pos_api_java.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public VoucherDTO findValidVoucher(String id) {
        Voucher voucher = voucherRepository.findValidVoucher(id);
        if (voucher == null) return null;
        return VoucherDTO.builder()
                .id(voucher.getId())
                .discount(voucher.getDiscount())
                .quantity(voucher.getQuantity())
                .expirationDate(voucher.getExpirationDate())
                .build();
    }
}
