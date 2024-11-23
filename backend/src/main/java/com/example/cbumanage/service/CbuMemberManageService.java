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
	public List<CbuMember> getMembersWithoutDues(String term) {
		return memberRepository.findAllWithoutDues(term);
	}

	@Transactional
	public CbuMember createUser(Long adminMemberId, MemberCreateDTO memberCreateDTO) {
		Optional<CbuMember> _adminMember = memberRepository.findById(adminMemberId);
		CbuMember adminMember = _adminMember.orElseThrow();
		if (!adminMember.getRole().contains(Role.ADMIN)) return null;

		CbuMember member = cbuMemberMapper.map(memberCreateDTO, true);
		if (member == null) {
			return null;
		}
		memberRepository.save(member);
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


	@Transactional
	public boolean addDues(long memberId, String term) {
		return addDues(memberRepository.findById(memberId).orElseGet(() -> null), term);
	}

	@Transactional
	public boolean addDues(CbuMember member, String term) {
		if (member == null) {
			return false;
		}

		Dues dues = new Dues();
		dues.setMemberId(member.getCbuMemberId());
		dues.setTerm(term);
		duesRepository.save(dues);
		return true;
	}

	@Transactional
	public boolean removeDues(long memberId, String term) {
		AtomicReference<Boolean> success = new AtomicReference<>(true);
		memberRepository.findById(memberId).ifPresentOrElse(
				member -> duesRepository.findByMemberIdAndTerm(member.getCbuMemberId(), term).ifPresentOrElse(duesRepository::delete,() -> success.set(false))
		, () -> success.set(false));
		return success.get();
	}
}
