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
import org.springframework.lang.Nullable;


@Data
@ToString(includeFieldNames = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntityBase {
  @Id
  @Column(name = "id", length = Constants.ID_MAX_LENGTH)
  @Size(max  = Constants.ID_MAX_LENGTH)
  @UuidGenerator(name="UUID")
  @GeneratedValue(generator = "UUID")
  private String id;

  @Nullable
  @Column(name = "is_active", nullable = true)
  private boolean isActive = true;

  @Nullable
  @Column(name = "is_deleted", nullable = true)
  private boolean isDeleted;

  @Nullable
  @CreatedDate
  @Column(name = "created_date", updatable = false, nullable = true, columnDefinition = "TIMESTAMP")
  private LocalDateTime createdDate = LocalDateTime.now();

  @Nullable
  @LastModifiedDate
  @Column(name = "modified_date", columnDefinition = "TIMESTAMP")
  private LocalDateTime modifiedDate  = LocalDateTime.now();

  @Nullable
  @CreatedBy
  @Column(name = "created_user", updatable = true, nullable = true)
  private String createdUser = "SYSTEM";

  @Nullable
  @LastModifiedBy
  @Column(name = "modified_user")
  private String modifiedUser;
}
