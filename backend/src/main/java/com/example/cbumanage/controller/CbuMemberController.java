package com.example.cbumanage.controller;

import com.example.cbumanage.dto.MemberCreateDTO;
import com.example.cbumanage.dto.MemberDTO;
import com.example.cbumanage.dto.MemberUpdateDTO;
import com.example.cbumanage.model.CbuMember;
import com.example.cbumanage.repository.CbuMemberRepository;
import com.example.cbumanage.service.CbuMemberManageService;
import com.example.cbumanage.service.CbuMemberSyncService;
import com.example.cbumanage.utils.CbuMemberMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        this.cbuMemberSyncService = cbuMemberSyncService;
        this.cbuMemberManageService = cbuMemberManageService;
        this.cbuMemberRepository = cbuMemberRepository;
        this.cbuMemberMapper = cbuMemberMapper;
    }

    @PostMapping("/members/sync")
    public String memberSync(){
        try{
            cbuMemberRepository.deleteAll();
            cbuMemberSyncService.syncMembersFromGoogleSheet();
            return "멤버 저장 성공!";
        }catch (Exception e){
            return String.valueOf(e);
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
