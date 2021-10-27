package com.example.security.dto.group;

import com.example.common.dto.request.DateRange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchGroupRequest {
	
	String name;
	DateRange createDate;
	Boolean isActive;
}