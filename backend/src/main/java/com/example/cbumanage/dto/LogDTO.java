package com.example.cbumanage.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class LogDTO {
    private Long logId;
    private Date date;
    private String logUser;
    private String logData;
}
