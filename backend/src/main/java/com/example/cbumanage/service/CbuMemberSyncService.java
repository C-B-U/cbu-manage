package com.example.cbumanage.service;

import com.example.cbumanage.model.CbuMember;
import com.example.cbumanage.repository.CbuMemberRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class CbuMemberSyncService {
    @Autowired
    CbuMemberRepository cbuMemberRepository;

    @Value("${google.spreadSheet.key}")
    private static String SheetKey;                        //구글 스프레드 시트 사용 api 키 값

    @Value("${google.spreadSheet.Id}")
    private static String SheetId;                         //구글 스프레드 시트 아이디 값
    private static final String SHEET_NAME = "시트 1";      //구글 스프레드 시트 시트 이름
    private static final String API_KEY = SheetKey;

    @Transactional
    public void syncMembersFromGoogleSheet() {                    //스프레드 시트 -> 데이터베이스 유저 데이터 주입
        List<CbuMember> members = getMembersFromGoogleSheet();    //스프레드 시트에서 값을 가져와 members에 저장 후
        cbuMemberRepository.saveAll(members);                     //레포지토리를 이용해 데이터베이스에 members에 저장
    }

    private List<CbuMember> getMembersFromGoogleSheet() {               //스프레드 시트 데이터 가져오는 함수
        URI sheetUrl = getSheetUri();                                   //커스텀 URI를 생성
        RestTemplate rt = new RestTemplate();
        ResponseEntity<GoogleSheetResponse> response = rt.exchange(     //Get 메소드를 이용해 구글에 요청 전송
                sheetUrl,
                HttpMethod.GET,
                null,
                GoogleSheetResponse.class
        );

        GoogleSheetResponse sheetResponse = response.getBody();         //응답에서 body값을 가져와서
        List<List<Object>> values = sheetResponse.getValues();

        List<CbuMember> members = new ArrayList<>();                    //arrayList로 매핑
        for (int i = 1; i < values.size(); i++) {
            List<Object> row = values.get(i);
            CbuMember member = mapRowToMember(row);
            members.add(member);
        }

        return members;
    }

    private URI getSheetUri() {       //api key, 스프레드 시트 아이디와 이름을 URI에 주입
        return UriComponentsBuilder
                .fromUriString("https://sheets.googleapis.com/v4/spreadsheets/{sheetId}/values/{sheetName}")
                .queryParam("key", API_KEY)
                .buildAndExpand(SheetId, SHEET_NAME)
                .toUri();
    }

    private CbuMember mapRowToMember(List<Object> row) {     //멤버 데이터값을 String 값으로 변환해 매핑 (스프레드시트에 빈칸이 있으면 작동이 멈춤 해결방법 찾는중,,,)
        CbuMember member = new CbuMember();
        member.setName((String) row.get(0));
        member.setRole(List.of());
        member.setPhoneNumber((String) row.get(1));
        member.setMajor((String) row.get(2));
        member.setGrade((String) row.get(3));
        member.setStudentNumber(Long.parseLong(row.get(4).toString()));
        member.setGeneration(Long.parseLong(row.get(5).toString()));
//        member.setOngoingStudy(row.get(6) != "" ? (String) row.get(6) : null);
        member.setNote(row.get(7) != "" ? (String) row.get(7) : null);
        member.setKakaoNoti(row.get(9) != "" ? (String) row.get(9) : null);
        member.setKakaoChat(row.get(10) != "" ? (String) row.get(10) : null);
        return member;
    }
}

@Getter
class GoogleSheetResponse {
    private List<List<Object>> values;

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }
}

