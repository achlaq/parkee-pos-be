package com.parkee.pos_api_java.repository;

import com.parkee.pos_api_java.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByPlateNumberAndDeletedDateIsNull(String plateNumber);
}