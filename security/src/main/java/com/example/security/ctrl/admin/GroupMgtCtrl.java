package com.example.security.ctrl.admin;


import com.example.common.dto.response.InsertSuccessResponse;
import com.example.common.dto.response.SuccessResponse;
import com.example.security.dto.group.CreateGroupRequest;
import com.example.security.dto.group.GetAllGroups;
import com.example.security.dto.group.UpdateGroupRequest;
import com.example.security.dto.groupuser.AddMemberToGroup;
import com.example.security.entity.Group;
import com.example.security.entity.UserGroupRel;
import com.example.security.repo.UserGroupRelRepo;
import com.example.security.service.GroupService;
import com.example.security.service.UserGroupService;
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

    @Autowired
    private UserGroupRelRepo userGroupRelRepo;

    @Autowired
    private UserGroupService userGroupService;

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

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse update(@Valid @RequestBody UpdateGroupRequest ugr) {
        Group gr = groupService.update(ugr);
        return new SuccessResponse();
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

	//manager
    @GetMapping("allmember/name/get")
    @ResponseBody
    public List<UserGroupRel> getallmember(String groupName) throws NotFoundException {
        return userGroupRelRepo.allMemberinGroupbyId(groupName);
    }

    @PostMapping("addmember")
    @ResponseStatus(HttpStatus.OK)
    public InsertSuccessResponse addmembertoGroup(@Valid @RequestBody AddMemberToGroup addMemberToGroup) throws NotFoundException{
        UserGroupRel ugr = userGroupService.addmember(addMemberToGroup);
        return new InsertSuccessResponse(ugr.getId());
    }

    @PutMapping("removemember/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse removeMemberinGroup(@PathVariable("id") String id) throws SQLException{
       userGroupService.removemember(id);
        return new SuccessResponse();
    }

}
