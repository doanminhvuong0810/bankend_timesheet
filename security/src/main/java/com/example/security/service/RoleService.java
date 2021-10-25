package com.example.security.service;

import com.example.security.dto.role.CreateRoleUserRequest;
import com.example.security.entity.Role;

/**
 * @author Pham Duc Manh
 *
 */
public interface RoleService {
    Role create(CreateRoleUserRequest createRoleUserRequest);
}
