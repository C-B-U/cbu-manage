package com.example.cbumanage.service;

import com.example.cbumanage.model.CbuMember;
import com.example.cbumanage.repository.CbuMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class CbuMemberService {
    @Autowired
    CbuMemberRepository cbuMemberRepository;

    private static final String SHEET_ID = "sdfs";
    private static final String SHEET_NAME = "시트1";
    private static final String API_KEY = "sfsrg";

    @Transactional
    public void syncMembersFromGoogleSheet() {
        List<CbuMember> members = getMembersFromGoogleSheet();
        cbuMemberRepository.saveAll(members);
    }

    private List<CbuMember> getMembersFromGoogleSheet() {
        URI sheetUrl = getSheetUri();
        RestTemplate rt = new RestTemplate();
        ResponseEntity<GoogleSheetResponse> response = rt.exchange(
                sheetUrl,
                HttpMethod.GET,
                null,
                GoogleSheetResponse.class
        );

        GoogleSheetResponse sheetResponse = response.getBody();
        List<List<Object>> values = sheetResponse.getValues();

        List<CbuMember> members = new ArrayList<>();
        for (int i = 1; i < values.size(); i++) {
            List<Object> row = values.get(i);
            CbuMember member = mapRowToMember(row);
            members.add(member);
        }

        return members;
    }

    private URI getSheetUri() {
        return UriComponentsBuilder
                .fromUriString("https://sheets.googleapis.com/v4/spreadsheets/{sheetId}/values/{sheetName}")
                .queryParam("key", API_KEY)
                .buildAndExpand(SHEET_ID, SHEET_NAME)
                .toUri();
    }

    private CbuMember mapRowToMember(List<Object> row) {
        CbuMember member = new CbuMember();
        member.setName((String) row.get(0));
        member.setPhoneNumber((String) row.get(1));
        member.setMajor((String) row.get(2));
        member.setGrade((String) row.get(3));
        member.setStudentNumber(Long.parseLong(row.get(4).toString()));
        member.setGeneration(Long.parseLong(row.get(5).toString()));
        member.setOngoingStudy(row.get(6) != "" ? (String) row.get(6) : null);
        member.setNote(row.get(7) != "" ? (String) row.get(7) : null);
        member.setDues(row.get(8) != "" ? (String) row.get(8) : null);
        member.setKakaoNoti(row.get(9) != "" ? (String) row.get(9) : null);
        member.setKakaoChat(row.get(10) != "" ? (String) row.get(10) : null);
        return member;
    }
}

class GoogleSheetResponse {
    private List<List<Object>> values;

    public List<List<Object>> getValues() {
        return values;
    }

    public void setValues(List<List<Object>> values) {
        this.values = values;
    }
}

