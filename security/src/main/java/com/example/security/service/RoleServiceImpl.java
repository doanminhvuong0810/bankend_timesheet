package com.example.security.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.example.security.dto.role.CreateRoleUserRequest;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.repo.RoleRepo;
import com.example.security.repo.UserRepo;

/**
 * @author Pham Duc Manh
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService{
  @Autowired
  private RoleRepo roleRepo;
  @Autowired
  private UserRepo userRepo;
  
  @Override
  public Role create(@Valid CreateRoleUserRequest createRoleUserRequest) {
//    Optional<User> oUser = userRepo.findById(id);
//    if (!oUser.isPresent()) {
//      throw new NotFoundException("common.error.not-found");
//    } else {
    try {
      String name = createRoleUserRequest.getName();
      Role role =  roleRepo.findRoleByName(name);
//      if(rol)
      if( role != null &&  role.getName().equals(name) ) {
        throw new DuplicateKeyException("common.error.dupplicate");
      } else {
      Role r = new Role();
      PropertyUtils.copyProperties(r, createRoleUserRequest);
      return roleRepo.save(r);
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
}
//}
