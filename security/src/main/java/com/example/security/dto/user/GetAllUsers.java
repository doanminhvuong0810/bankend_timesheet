package com.example.security.dto.user;

import java.time.LocalDateTime;
import java.util.List;

import com.example.security.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Pham Duc Manh
 *
 */
  @Getter
  @Setter
public class GetAllUsers {
    private String id;
    private String name;
    private String displayName;
    private String password;
    private Integer failLoginCount;
    private boolean isLock;
    private LocalDateTime affectDate;
    private LocalDateTime expireDate;
    private Long testAmount;
    private String testSelectId;
    private UserTypeEnum userType;
    private String fullTextSearch;
}
