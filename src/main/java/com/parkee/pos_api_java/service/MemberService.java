package com.parkee.pos_api_java.service;

import com.parkee.pos_api_java.dto.MemberDTO;
import com.parkee.pos_api_java.entity.Member;
import com.parkee.pos_api_java.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberDTO getMember(String plateNumber) {
        Member member = memberRepository.findByPlateNumberAndDeletedDateIsNull(plateNumber);
        if (member == null) return null;
        return MemberDTO.builder()
                .name(member.getName())
                .plateNumber(member.getPlateNumber())
                .memberExpiredDate(member.getMemberExpiredDate())
                .build();
    }
}
