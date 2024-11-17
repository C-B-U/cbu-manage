package com.example.cbuManage.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private Date date; //로그가 생성된 시점

    private String logUser; //로그를 생성한 유저

    private String logData; //로그 내용

}
