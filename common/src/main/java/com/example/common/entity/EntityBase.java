package com.example.common.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.common.config.Constants;

import lombok.Data;
import lombok.ToString;


@Data
@ToString(includeFieldNames = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntityBase {
  @Id
  @NotNull
  @Column(name = "id", length = Constants.ID_MAX_LENGTH)
  @Size(max  = Constants.ID_MAX_LENGTH)
  @UuidGenerator(name="UUID")
  @GeneratedValue(generator = "UUID")
  private String id;

  @NotNull
  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;

  @NotNull
  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = true;
  
  @NotNull
  @CreatedDate
  @Column(name = "created_date", updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "modified_date", columnDefinition = "TIMESTAMP")
  private LocalDateTime modifiedDate;
  
  @NotNull
  @CreatedBy
  @Column(name = "created_user", updatable = true, nullable = false)
  private String createdUser;

  @LastModifiedBy
  @Column(name = "modified_user")
  private String modifiedUser;
}
