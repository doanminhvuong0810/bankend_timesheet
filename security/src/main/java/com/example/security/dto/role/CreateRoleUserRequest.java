package com.example.security.dto.role;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.security.entity.Role;
import com.example.security.entity.UserRoleRel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Pham Duc Manh
 *
 */
@Getter
@Setter
public class CreateRoleUserRequest {

  @NotNull
  @Size(max = Role.NAME_MAX_LENGTH, min = Role.NAME_MIN_LENGTH)
  private String name;
  
}
