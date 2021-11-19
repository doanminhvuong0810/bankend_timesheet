package com.example.security.dto.user;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.example.security.enums.UserTypeEnum;
import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
    private String bankName;
    private String bankNumber;
    private Date birthDay;
    private String staffID;
    private String phone;
    private String email;
    private Integer failLoginCount;
    private boolean isLock;
    private LocalDateTime affectDate;
    private LocalDateTime expireDate;
    private Long testAmount;
    private String testSelectId;
    private UserTypeEnum userType;
    private String fullTextSearch;
}
