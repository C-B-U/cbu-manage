package com.example.cbuManage.Controller;

import com.example.cbuManage.Repository.CbuMemberRepository;
import com.example.cbuManage.Service.CbuMemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CbuMemberController {
    @Autowired
    CbuMemberService cbuMemberService;

    @Autowired
    CbuMemberRepository cbuMemberRepository;

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