package com.example.security.ctrl.admin;


import com.example.common.dto.response.InsertSuccessResponse;
import com.example.common.dto.response.SuccessResponse;
import com.example.security.dto.group.CreateGroupRequest;
import com.example.security.dto.group.GetAllGroups;
import com.example.security.entity.Group;
import com.example.security.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/guest/groups")
public class GroupMgtCtrl {
    @Autowired
    private GroupService groupService;

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    public List<GetAllGroups> getAllGroups(){
        return groupService.getAllGroups();
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.OK)
    public InsertSuccessResponse create(@Valid @RequestBody CreateGroupRequest group) {
        Group g = groupService.create(group);
        return new InsertSuccessResponse(g.getId());
    }
    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse deleteByIdIn(@RequestBody List<String> ids) throws SQLException {
        groupService.deleteByIdIn(ids);
        return new SuccessResponse();
    }
    @GetMapping("get/{id}")
	public Optional<Group> findById(@PathVariable("id") String id) throws NotFoundException {
		return groupService.findById(id);
	}
}
