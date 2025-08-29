package com.parkee.pos_api_java.controller;

import com.parkee.pos_api_java.dto.MemberDTO;
import com.parkee.pos_api_java.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/get/{plateNumber}")
    public ResponseEntity<Object> getMemberDetail(@PathVariable String plateNumber) {

        try {
            MemberDTO memberDto = memberService.getMember(plateNumber);
            return ResponseEntity.ok(memberDto);
        } catch (RuntimeException e) {
            log.error("#MemberController#getMemberDetail ERROR! with plateNumber: {} and error: {}", plateNumber,
                    e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
