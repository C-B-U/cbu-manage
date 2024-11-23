package com.example.cbumanage.controller;

import com.example.cbumanage.dto.MemberCreateDTO;
import com.example.cbumanage.dto.MemberDTO;
import com.example.cbumanage.model.CbuMember;
import com.example.cbumanage.repository.CbuMemberRepository;
import com.example.cbumanage.service.CbuMemberManageService;
import com.example.cbumanage.service.CbuMemberSyncService;
import com.example.cbumanage.utils.CbuMemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CbuMemberController {
    @Autowired
    CbuMemberSyncService cbuMemberSyncService;
    @Autowired
    CbuMemberManageService cbuMemberManageService;
    @Autowired
    CbuMemberRepository cbuMemberRepository;
    @Autowired
    CbuMemberMapper cbuMemberMapper;



//    @GetMapping("/saveMemberToDatabase")
//    public String getMember(){
//        try{
//            cbuMemberRepository.deleteAll();
//            cbuMemberSyncService.syncMembersFromGoogleSheet();
//            return "멤버 저장 성공!";
//        }catch (Exception e){
//            String a = String.valueOf(e);
//            return a;
//        }
//    }

    @PostMapping("/member")
    public ResponseEntity<String> postMember(@RequestBody MemberCreateDTO memberCreateDTO){
        try{
            cbuMemberManageService.createUser(1L, memberCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("멤버 저장 성공!");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("멤버 저장 실패!");
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

    @GetMapping("/members/dues")
    public ResponseEntity<?> getMembersByDues(
            @RequestParam("term") String term,
            @RequestParam(name = "paid", required = false) Boolean paid
//            @RequestParam(name = "page", required = false) Integer page
    ){
        try{
            if(paid != null && paid){
                return ResponseEntity.internalServerError().body("Not implemented yet");
            } else {
                return ResponseEntity.ok(cbuMemberMapper.map(cbuMemberManageService.getMembersWithoutDues(term)));
            }
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
