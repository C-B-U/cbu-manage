package com.example.cbumanage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {
	private Long id;
	private String name;
	private String phoneNumber;
	private String major;
	private String grade;
	private Long studentNumber;
	private Long generation;
	private String note;
}