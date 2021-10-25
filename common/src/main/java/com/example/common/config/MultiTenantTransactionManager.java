package com.example.common.config;

import javax.persistence.EntityManager;

import org.springframework.orm.jpa.JpaTransactionManager;

import com.example.common.model.ThreadContext;

public class MultiTenantTransactionManager extends JpaTransactionManager {
  private static final long serialVersionUID = 1L;

  @Override
  protected EntityManager createEntityManagerForTransaction() {
    EntityManager em = super.createEntityManagerForTransaction();
    em.setProperty(Constants.TENANT_ID_PROP, ThreadContext.getTenantId());
    return em;
  }
}
