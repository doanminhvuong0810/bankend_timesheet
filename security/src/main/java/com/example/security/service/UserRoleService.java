package com.example.security.service;

import com.example.security.dto.role.RoleForAddUserToRole;
import com.example.security.entity.Role;
import com.example.security.entity.UserRoleRel;

/**
 * @author Pham Duc Manh
 *
 */
public interface UserRoleService {
  UserRoleRel addToRole(RoleForAddUserToRole addUserToRole);
}
