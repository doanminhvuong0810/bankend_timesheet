package com.example.security.dto.role;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Pham Duc Manh
 *
 */

@Getter
@Setter
public class RoleForAddUserToRole {
  
  @NotNull
  private String userName;
  
  @NotNull
  private String roleName;
  
  private String createdUser;
}
