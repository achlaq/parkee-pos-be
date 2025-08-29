package com.parkee.pos_api_java.repository;

import com.parkee.pos_api_java.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoucherRepository extends JpaRepository<Voucher, String> {

    @Query("SELECT v FROM Voucher v WHERE v.id = :id AND v.quantity > 0 AND v.expirationDate > CURRENT_TIMESTAMP AND v.deletedDate IS null")
    Voucher findValidVoucher(String id);
}
