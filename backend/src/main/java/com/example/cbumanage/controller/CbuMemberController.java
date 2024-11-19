package com.example.cbumanage.controller;

import com.example.cbumanage.repository.CbuMemberRepository;
import com.example.cbumanage.service.CbuMemberService;
import com.example.cbumanage.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CbuMemberController {
    @Autowired
    CbuMemberService cbuMemberService;

    @Autowired
    CbuMemberRepository cbuMemberRepository;

    @Autowired
    LogService logService;

    @GetMapping("/api/v1/saveMemberToDatabase")
    public String getMember(){
        try{
            cbuMemberRepository.deleteAll();
            cbuMemberService.syncMembersFromGoogleSheet();
            return "멤버 저장 성공!";
        }catch (Exception e){
            String a = String.valueOf(e);
            return a;
        }
    }

}
