package com.example.security.ctrl.admin;

import com.example.common.dto.response.SuccessResponse;
import com.example.security.dto.role.CreateRoleUserRequest;
import com.example.security.dto.role.RoleForAddUserToRole;
import com.example.security.repo.RoleRepo;
import com.example.security.repo.UserRepo;
import com.example.security.repo.UserRoleRelRepo;
import com.example.security.service.RoleService;
import com.example.security.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v2/guest/role")
@Validated
public class RoleMgtCtrl {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/newrole")
    @ResponseBody
    public SuccessResponse newrole(@Valid @RequestBody CreateRoleUserRequest createRoleUserRequest) {
        roleService.create(createRoleUserRequest);
        return new SuccessResponse();
    }
    @PostMapping("/addUserToRole")
    @ResponseBody
    public SuccessResponse addUserToRole(@Valid @RequestBody RoleForAddUserToRole roleForAddUserToRole, Principal principal) {
        userRoleService.addToRole(roleForAddUserToRole);
        return new SuccessResponse();
    }
}