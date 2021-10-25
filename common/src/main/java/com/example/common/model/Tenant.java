package com.example.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Tenant {
  private String hostName;
  private String tenantId;
  private boolean isInitialized;
}