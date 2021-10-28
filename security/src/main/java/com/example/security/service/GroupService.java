package com.example.security.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.security.dto.group.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.common.dto.request.PatchRequest;
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

    void addUserToGroup(GroupForAdminRequest request);

	void removeUserFromGroup(GroupForAdminRequest request);
}