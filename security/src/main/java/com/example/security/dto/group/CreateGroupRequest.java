package com.example.security.dto.group;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.security.entity.Group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupRequest {

	@NotNull
	@Size(max = Group.NAME_MAX_LENGTH, min = Group.NAME_MIN_LENGTH)
	private String name;
}