package com.example.cbumanage.controller;

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
    LoginService loginService;

    @GetMapping("/api/v1/getLoginKey")
    public List<String> getLoginKey(@RequestBody HashMap<String, Object> map){
        String studentId = (String) map.get("studentId");
        String studentPw = (String) map.get("studentPw");
        return loginService.getKeys(studentId, studentPw);
    }

}
