package com.example.security.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.common.dto.request.PatchRequest;
import com.example.security.dto.group.CreateGroupRequest;
import com.example.security.dto.group.GetAllGroups;
import com.example.security.dto.group.SearchGroupRequest;
import com.example.security.dto.group.UpdateGroupRequest;
import com.example.security.entity.Group;

public interface GroupService {

	List<GetAllGroups> getAllGroups();

	Optional<Group> findById(String id);

	Group create(CreateGroupRequest group);

	Group updateAllFields(String id, UpdateGroupRequest group);

	Group updateSomeFields(String id, @Valid PatchRequest<UpdateGroupRequest> patchRequest);

	void deleteById(String id);

	int deleteByIdIn(List<String> ids);

	Group setActive(String id, boolean isActive);

	Page<Group> advanceSearch(String filter, SearchGroupRequest searchRequest, Pageable pageable);

	Optional<Group> findByNameIgnoreCase(String name);

	Page<Group> findAll(Pageable pageable);

	Page<Group> findByFullTextSearchContains(String filter, Pageable pageable);

}