package com.example.cbumanage.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MemberCreateDTO {
	private String name;
	private String phoneNumber;
	private String major;
	private String grade;
	private Long studentNumber;
	private Long generation;
	private String note;
}
