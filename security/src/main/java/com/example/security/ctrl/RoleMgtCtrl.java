package com.example.security.ctrl;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.dto.response.SuccessResponse;
import com.example.security.dto.role.CreateRoleUserRequest;
import com.example.security.dto.role.RoleForAddUserToRole;
import com.example.security.entity.Role;
import com.example.security.repo.RoleRepo;
import com.example.security.repo.UserRepo;
import com.example.security.repo.UserRoleRelRepo;
import com.example.security.service.RoleService;
import com.example.security.service.UserRoleService;

/**
 * @author Pham Duc Manh
 *
 */
@RestController
@RequestMapping("/api/v1/admin/role")
@Validated
public class RoleMgtCtrl {

   @Autowired
   private RoleService roleService;

   @Autowired
   private UserRoleRelRepo userRoleRelRepo;
   
   @Autowired
   private RoleRepo roleRepo;
   
   @Autowired
   private UserRepo userRepo;
   
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
