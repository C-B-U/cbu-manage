package com.example.cbumanage.controller;

import com.example.cbumanage.dto.EmailAuthResponseDTO;
import com.example.cbumanage.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmailController {

    private final EmailService emailService;

    // 인증번호 전송
    @GetMapping("/mail")
    public EmailAuthResponseDTO sendAuthCode(@RequestParam String address) {
        return emailService.sendEmail(address);
    }

    // 인증번호 검증
    @PostMapping("/mail")
    public EmailAuthResponseDTO checkAuthCode(@RequestParam String address, @RequestParam String authCode) {
        return emailService.validateAuthCode(address, authCode);
    }
}
