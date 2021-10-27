package com.example.security.dto.group;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.common.config.Constants;
import com.example.security.entity.Group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGroupRequest {

	@NotNull
	@Size(min = Group.NAME_MIN_LENGTH, max = Group.NAME_MAX_LENGTH)
	private String name;

	@Size(min = Constants.ID_MAX_LENGTH, max = Constants.ID_MAX_LENGTH)
	private String tenantId;

	@Size(max = Group.FULL_TEXT_SEARCH_MAX_LENGTH)
	private String fullTextSearch;
}