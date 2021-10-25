package com.example.security.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.example.common.enums.TokenTypeEnum;
import com.example.common.model.SecurityConfigParam;
import com.example.common.util.SecurityUtil;
import com.example.security.dto.role.RoleForAddUserToRole;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.entity.UserRoleRel;
import com.example.security.repo.RoleRepo;
import com.example.security.repo.UserRepo;
import com.example.security.repo.UserRoleRelRepo;

/**
 * @author Pham Duc Manh
 *
 */
@Service 
@Transactional
public class UserRoleImpl implements UserRoleService {
  
   @Autowired
   TokenService tokenService;
   
   @Autowired
   UserRoleRelRepo userRoleRelRepo;

   @Autowired
   private SecurityConfigParam securityParam;
   
   @Autowired 
   private UserRepo userRepo;
   @Autowired 
   private RoleRepo roleRepo;
//   @Override
//  public  addToRole(@Valid RoleForAddUserToRole addUserToRole) {
//  
//   
//    }
   

  @Override
  public UserRoleRel addToRole(RoleForAddUserToRole addUserToRole) {
//    Optional<User> oUser = userRepo.findById(addUserToRole.getIdUser());
    String token = securityParam.getTokenPrefix();
     
    System.out.println("token = " + token);
//    try {
//      User user =  userRepo.findBy
//      String token =(oUser.get().getId());
//      Optional<T>
      
//      String roleName = userRoleRelRepo.findByIdUser().getName();
//      userRoleRelRepo.f
//    if(roleName == "ADMIN") {
    String roleNameWrite = addUserToRole.getRoleName();
    
    if(roleNameWrite.equalsIgnoreCase("ROLE_ADMIN") || roleRepo.findRoleByName(addUserToRole.getRoleName())== null) {
      throw new NotFoundException("common.error.not-found");
    } 
    if(userRepo.findByName( addUserToRole.getUserName()).get() == null) {
      throw new NotFoundException("common.error.not-found");
    }
     String roleId = roleRepo.findRoleByName(addUserToRole.getRoleName()).getId();
     String userId =  userRepo.findByName( addUserToRole.getUserName()).get().getId();
     
     
     
     
     UserRoleRel userRoleRel = userRoleRelRepo.findUserRoleByUserIdAndRoleId(userId, roleId);
     if(userRoleRel == null) {
       UserRoleRel userRoleRel1 = new UserRoleRel();
       userRoleRel1.setUserId(userId);
        userRoleRel1.setRoleId(roleId); 
        userRoleRel1.setCreatedUser("admin");
        userRoleRelRepo.save(userRoleRel1);
     } else {
       throw new DuplicateKeyException("common.error.dupplicate");
     }
//    } else {
//      throw new NotFoundException("common.error.not-found");
//    }
    return null;
//    } catch(Exception e){
//      throw new IllegalArgumentException("common.error.not-valid-token");
//    }
//  }
    }
}
