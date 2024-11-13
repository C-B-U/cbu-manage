package com.example.cbuManage.Service;

import com.example.cbuManage.Model.Log;
import com.example.cbuManage.Repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogService {
    @Autowired
    LogRepository logRepository;
    public void createLog(Log log){
        logRepository.save(log);
    }
}
