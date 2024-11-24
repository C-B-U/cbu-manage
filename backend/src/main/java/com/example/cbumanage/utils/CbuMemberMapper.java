package com.example.cbumanage.utils;

import com.example.cbumanage.dto.MemberCreateDTO;
import com.example.cbumanage.dto.MemberDTO;
import com.example.cbumanage.model.CbuMember;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CbuMemberMapper {
	public CbuMember map(MemberCreateDTO memberCreateDTO, boolean checkNull) {
		if (checkNull &&
				(memberCreateDTO.getName() == null ||
				memberCreateDTO.getPhoneNumber() == null ||
				memberCreateDTO.getMajor() == null ||
				memberCreateDTO.getGrade() == null ||
				memberCreateDTO.getStudentNumber() == null ||
				memberCreateDTO.getGeneration() == null ||
				memberCreateDTO.getNote() == null
		)) {
			return null;
		}
		CbuMember cbuMember = new CbuMember();
		cbuMember.setName(memberCreateDTO.getName());
		cbuMember.setPhoneNumber(memberCreateDTO.getPhoneNumber());
		cbuMember.setMajor(memberCreateDTO.getMajor());
		cbuMember.setGrade(memberCreateDTO.getGrade());
		cbuMember.setStudentNumber(memberCreateDTO.getStudentNumber());
		cbuMember.setGeneration(memberCreateDTO.getGeneration());
		cbuMember.setNote(memberCreateDTO.getNote());
		return cbuMember;
	}
	public MemberDTO map(CbuMember cbuMember) {
		MemberDTO memberDTO = new MemberDTO(cbuMember.getCbuMemberId(), cbuMember.getName(), cbuMember.getPhoneNumber(), cbuMember.getMajor(), cbuMember.getGrade(), cbuMember.getStudentNumber(), cbuMember.getGeneration(), cbuMember.getNote());
		return memberDTO;
	}
	public List<MemberDTO> map(List<CbuMember> cbuMember) {
		return cbuMember.stream().map(this::map).toList();
	}
}
