package com.example.cbumanage.service;

import com.example.cbumanage.dto.MemberCreateDTO;
import com.example.cbumanage.dto.MemberUpdateDTO;
import com.example.cbumanage.model.*;
import com.example.cbumanage.model.enums.LogDataType;
import com.example.cbumanage.model.enums.LogType;
import com.example.cbumanage.model.enums.Role;
import com.example.cbumanage.repository.CbuMemberRepository;
import com.example.cbumanage.repository.DuesRepository;
import com.example.cbumanage.repository.LogRepository;
import com.example.cbumanage.utils.CbuMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CbuMemberManageService {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private CbuMemberRepository memberRepository;
	private DuesRepository duesRepository;
	private LogRepository logRepository;

	private CbuMemberMapper cbuMemberMapper;

	@Autowired
	public CbuMemberManageService(CbuMemberRepository memberRepository, DuesRepository duesRepository, LogRepository logRepository, CbuMemberMapper cbuMemberMapper) {
		this.memberRepository = memberRepository;
		this.duesRepository = duesRepository;
		this.logRepository = logRepository;
		this.cbuMemberMapper = cbuMemberMapper;
	}

	@Transactional
	public List<CbuMember> getMembers(int page) {
		Page<CbuMember> memberPage = memberRepository.findAll(PageRequest.of(page, 10));
		return memberPage.getContent();
	}
	@Transactional
	public List<CbuMember> getMembersWithoutDues(String term) {
		return memberRepository.findAllWithoutDues(term);
	}

	@Transactional
	public CbuMember createUser(Long adminMemberId, MemberCreateDTO memberCreateDTO) {
		Optional<CbuMember> _adminMember = memberRepository.findById(adminMemberId);
		CbuMember adminMember = _adminMember.orElseThrow();
		if (!adminMember.getRole().contains(Role.ADMIN)) throw new RuntimeException("You don't have permission");

		CbuMember member = cbuMemberMapper.map(memberCreateDTO, true);
		if (member == null) {
			return null;
		}
		Log log = new Log(adminMemberId, LogType.CREATE, LogDataType.USER_ENTITY, "Create:cbu_member(" + member + ")");

		memberRepository.save(member);
		logRepository.save(log);

		return member;
	}

	@Transactional
	public boolean updateUser(Long adminMemberId, MemberUpdateDTO memberUpdateDTO) {
		Optional<CbuMember> _member = memberRepository.findById(memberUpdateDTO.getCbuMemberId());
		CbuMember member = _member.orElseThrow();

		Optional<CbuMember> _adminMember = memberRepository.findById(adminMemberId);
		CbuMember adminMember = _adminMember.orElseThrow();
		if (!adminMember.getRole().contains(Role.ADMIN)) return false;

		// role
		if (memberUpdateDTO.getRole() != null) {
			List<Role> oldRole = new ArrayList<>(member.getRole());
			member.setRole(memberUpdateDTO.getRole());
			Log log = new Log(adminMember.getCbuMemberId(),
					LogType.UPDATE,
					LogDataType.USER_ROLE,
					"Update:cbu_member.role(" + oldRole + " -> " + memberUpdateDTO.getRole() + ")");
			logRepository.save(log);
		}

		// name
		if (memberUpdateDTO.getName() != null) {
			String oldName = member.getName();
			member.setName(memberUpdateDTO.getName());
			Log log = new Log(adminMember.getCbuMemberId(),
					LogType.UPDATE,
					LogDataType.USER_NAME,
					"Update:cbu_member.name(" + oldName + " -> " + memberUpdateDTO.getName() + ")");
			logRepository.save(log);
		}

		return true;
	}
}
