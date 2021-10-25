package com.example.common.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(includeFieldNames = true)
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
  /**
   * 
   */
  private static final long serialVersionUID = -7615247446131926010L;
  private String id;
  private String username;
  private String displayName;
  private String tenantId;
  @JsonIgnore
  private String password;
  @Setter
  @JsonIgnore
  private boolean active;
  @Setter
  @JsonIgnore
  private boolean locked;
  @JsonIgnore
  private LocalDateTime affectDate;
  @JsonIgnore
  private LocalDateTime expireDate;
  @Setter
  @JsonIgnore
  private Collection<GrantedAuthority> authorities;

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    LocalDateTime c = LocalDateTime.now();
    return (affectDate == null || c.isAfter(affectDate))
        && (expireDate == null || c.isBefore(expireDate));
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return active;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return !locked;
  }

  public CustomUserDetails(String id, String username, String displayName, String password, boolean active,
      boolean locked, LocalDateTime affectDate, LocalDateTime expireDate, String tenantId) {
    super();
    this.id = id;
    this.username = username;
    this.displayName = displayName;
    this.password = password;
    this.active = active;
    this.locked = locked;
    this.affectDate = affectDate;
    this.expireDate = expireDate;
    this.tenantId = tenantId;
  }
}
