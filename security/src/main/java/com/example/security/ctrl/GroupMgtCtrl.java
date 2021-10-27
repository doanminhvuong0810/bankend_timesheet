package com.example.security.ctrl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import com.example.common.config.enums.SortOrderEnum;
import com.example.common.dto.request.PatchRequest;
import com.example.common.dto.response.InsertSuccessResponse;
import com.example.common.dto.response.PageResponse;
import com.example.common.dto.response.SuccessResponse;
import com.example.common.util.SearchUtil;
import com.example.common.util.StringUtil;
import com.example.security.dto.group.CreateGroupRequest;
import com.example.security.dto.group.SearchGroupRequest;
import com.example.security.dto.group.UpdateGroupRequest;
import com.example.security.entity.Group;
import com.example.security.service.GroupService;

@RestController
@RequestMapping("/api/v1/admin/groups")
public class GroupMgtCtrl {

	@Autowired
	private GroupService groupService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public InsertSuccessResponse create(@Valid @RequestBody CreateGroupRequest group) {
		Group g = groupService.create(group);
		return new InsertSuccessResponse(g.getId());
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse updateAllFields(@PathVariable String id, @Valid @RequestBody UpdateGroupRequest group)
			throws NotFoundException {
		Group g = groupService.updateAllFields(id, group);
		return new InsertSuccessResponse(g.getId());
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse updateSomeFields(@PathVariable("id") String id,
			@Valid @RequestBody PatchRequest<UpdateGroupRequest> patchRequest) throws NotFoundException {
		Group g = groupService.updateSomeFields(id, patchRequest);
		return new InsertSuccessResponse(g.getId());
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse deleteByIdIn(@RequestBody List<String> ids) throws SQLException {
		groupService.deleteByIdIn(ids);
		return new SuccessResponse();
	}

	@PostMapping("/active/{id}/{active}")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse setActive(@PathVariable("id") String id, @PathVariable("active") boolean isActive)
			throws NotFoundException {
		Group g = groupService.setActive(id, isActive);
		return new InsertSuccessResponse(g.getId());
	}

	@GetMapping("/{id}")
	public Optional<Group> findById(@PathVariable("id") String id) throws NotFoundException {
		return groupService.findById(id);
	}

	@GetMapping("/search/{name}")
	public Optional<Group> findByName(@PathVariable(name = "name") String name) {
		return groupService.findByNameIgnoreCase(name);
	}

	@GetMapping
	public PageResponse<Group> findAll(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
			@RequestParam(required = false) SortOrderEnum order) {
		Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		Page<Group> pageData = groupService.findAll(pageable);
		return new PageResponse<>(pageData);
	}

	@GetMapping("/search")
	public PageResponse<Group> fullTextSearch(@RequestParam(required = false) String filter,
			@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
			@RequestParam(required = false) SortOrderEnum order) {
		Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		Page<Group> pageData;
		if (filter == null || filter.length() == 0) {
			pageData = groupService.findAll(pageable);
		} else {
			filter = StringUtil.toFullTextSearch(filter);
			pageData = groupService.findByFullTextSearchContains(filter, pageable);
		}
		return new PageResponse<>(pageData);
	}

	@PostMapping(value = "/search")
	public PageResponse<Group> advanceSearch(@RequestParam(required = false) String filter,
			@Valid @RequestBody SearchGroupRequest searchRequest,
			@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
			@RequestParam(required = false) SortOrderEnum order) {
		Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		Page<Group> pageData = groupService.advanceSearch(filter, searchRequest, pageable);
		return new PageResponse<Group>(pageData);
	}
}