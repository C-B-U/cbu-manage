package com.example.cbumanage.controller;

import com.example.cbumanage.service.LogService;
import com.example.cbumanage.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class LoginController {

    @Autowired
    LoginService loginService;      //로그인 서비스 선언
    @Autowired
    LogService logService;          //로그 서비스 선언

    @GetMapping("/api/v1/getLoginKey")
    public List<String> getLoginKey(@RequestBody HashMap<String, Object> map){      //학교 로그인 키 전달 함수
        String studentId = (String) map.get("studentId");                           //클라이언트로부터 받아온 아이디
        String studentPw = (String) map.get("studentPw");                           //비밀번호를 학교 api로 요청을 보내 키값을 받아와
        return loginService.getKeys(studentId, studentPw);                          //클라이언트에게 전달
    }

}
