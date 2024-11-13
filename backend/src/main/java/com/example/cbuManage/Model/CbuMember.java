package com.example.cbuManage.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CbuMember")
public class CbuMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cbuMemberId;
    private String name; //이름
    private String phoneNumber; //전화번호
    private String major; //학과
    private String grade; //학년
    private Long studentNumber; //학번
    private Long generation; //기수
    private String ongoingStudy; //진행중인 스터디
    private String note; //비고
    private String dues; //회비 관리
    private String kakaoNoti; //공지방 가입 유무
    private String kakaoChat; //수다방 가입 유무
}
