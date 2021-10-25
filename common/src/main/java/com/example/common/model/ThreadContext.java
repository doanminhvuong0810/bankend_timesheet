package com.example.common.model;

import com.example.common.config.Constants;

public class ThreadContext {
  private static ThreadLocal<Tenant> currentTenant = new ThreadLocal<>();

  public static Tenant getCurrentTenant() {
    return currentTenant.get();
  }

  public static String getTenantId() {
    Tenant ct = currentTenant.get();
    if (ct!= null && ct.getTenantId() != null) {
      return ct.getTenantId();
    } else {
      return Constants.ZERO_UUID;
    }
  }

  public static void setCurrentTenant(Tenant tenant) {
    currentTenant.set(tenant);
  }
}