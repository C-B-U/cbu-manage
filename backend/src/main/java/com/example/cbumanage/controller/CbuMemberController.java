package com.example.cbumanage.controller;

import com.example.cbumanage.dto.MemberCreateDTO;
import com.example.cbumanage.dto.MemberDTO;
import com.example.cbumanage.dto.MemberUpdateDTO;
import com.example.cbumanage.model.CbuMember;
import com.example.cbumanage.repository.CbuMemberRepository;
import com.example.cbumanage.service.CbuMemberManageService;
import com.example.cbumanage.service.CbuMemberSyncService;
import com.example.cbumanage.utils.CbuMemberMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CbuMemberController {
    private final CbuMemberSyncService cbuMemberSyncService;
    private final CbuMemberManageService cbuMemberManageService;
    private final CbuMemberRepository cbuMemberRepository;
    private final CbuMemberMapper cbuMemberMapper;

    public CbuMemberController(CbuMemberSyncService cbuMemberSyncService, CbuMemberManageService cbuMemberManageService, CbuMemberRepository cbuMemberRepository, CbuMemberMapper cbuMemberMapper) {
        this.cbuMemberSyncService = cbuMemberSyncService;         //서비스 참조 선언
        this.cbuMemberManageService = cbuMemberManageService;     //서비스 참조 선언
        this.cbuMemberRepository = cbuMemberRepository;           //레포지토리 참조 선언
        this.cbuMemberMapper = cbuMemberMapper;
    }

    @PostMapping("/members/sync")
    public String memberSync(){                                     //스프레드시트 -> 데이터베이스 회원 데이터 주입 함수
        try{
            cbuMemberRepository.deleteAll();                        //데이터 주입 전 기존 데이터 전체 삭제: 삭제하기 전에 백업 한번 하는 함수 필요할 듯
            cbuMemberSyncService.syncMembersFromGoogleSheet();      //스프레드시트에서 데이터베이스로 데이터 값 주입
            return "멤버 저장 성공!";
        }catch (Exception e){                                       //오류가 났을 경우
            return String.valueOf(e);                               //오류 값 리턴
        }
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<?> getMember(@PathVariable Long id){
        try{
            Optional<CbuMember> _member = cbuMemberRepository.findById(id);
            if (!_member.isPresent()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(cbuMemberMapper.map(_member.get()));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/member")
    public ResponseEntity<String> postMember(@RequestBody MemberCreateDTO memberCreateDTO){
        try{
            CbuMember member = cbuMemberManageService.createUser(1L, memberCreateDTO);
            if (member == null) return ResponseEntity.internalServerError().body("멤버 저장 실패!");
            return ResponseEntity.status(HttpStatus.CREATED).body("멤버 저장 성공!");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("멤버 저장 실패!");
        }
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchMember(@RequestBody MemberUpdateDTO memberDTO) {
        Long adminMemberId = 1L;
        cbuMemberManageService.updateUser(adminMemberId, memberDTO);
    }

    @DeleteMapping("/member/{id}")
    public void deleteMember(@PathVariable Long id) {
        cbuMemberRepository.deleteById(id);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> getMembers(
            @RequestParam(name = "page", required = false) Integer page
    ){
        try{
            if (page == null) page = 0;
            return ResponseEntity.ok(cbuMemberMapper.map(cbuMemberManageService.getMembers(page)));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

//    @GetMapping("/members/dues")
//    public ResponseEntity<?> getMembersByDues(
//            @RequestParam("term") String term,
//            @RequestParam(name = "paid", required = false) Boolean paid
////            @RequestParam(name = "page", required = false) Integer page
//    ){
//        try{
//            if(paid != null && paid) {
//                return ResponseEntity.internalServerError().body("Not implemented yet");
//            }
//            return ResponseEntity.ok(cbuMemberMapper.map(cbuMemberManageService.getMembersWithoutDues(term)));
//        } catch (Exception e){
//            return ResponseEntity.internalServerError().body(null);
//        }
//    }

}
