package com.example.common.config;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.ClassUtils;

import com.example.common.model.ThreadContext;

public class MultiTenantEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
  @Override
  protected EntityManagerFactory createEntityManagerFactoryProxy(EntityManagerFactory emf) {
    Set<Class<?>> ifcs = new LinkedHashSet<>();
    Class<?> entityManagerFactoryInterface = null;
    try {
      Field f = MultiTenantEntityManagerFactoryBean.class.getField("entityManagerFactoryInterface");
      entityManagerFactoryInterface = (Class<?>) f.get(f);
    } catch (Exception e) {
    }
    if (entityManagerFactoryInterface != null) {
      ifcs.add(entityManagerFactoryInterface);
    } else if (emf != null) {
      ifcs.addAll(ClassUtils.getAllInterfacesForClassAsSet(emf.getClass(), this.getBeanClassLoader()));
    } else {
      ifcs.add(EntityManagerFactory.class);
    }
    ifcs.add(EntityManagerFactoryInfo.class);

    try {
      return (EntityManagerFactory) Proxy.newProxyInstance(this.getBeanClassLoader(), ClassUtils.toClassArray(ifcs),
          new ManagedEntityManagerFactoryInvocationHandler(this));
    } catch (IllegalArgumentException ex) {
      if (entityManagerFactoryInterface != null) {
        throw new IllegalStateException("EntityManagerFactory interface [" + entityManagerFactoryInterface
            + "] seems to conflict with Spring's EntityManagerFactoryInfo mixin - consider resetting the "
            + "'entityManagerFactoryInterface' property to plain [javax.persistence.EntityManagerFactory]", ex);
      } else {
        throw new IllegalStateException("Conflicting EntityManagerFactory interfaces - "
            + "consider specifying the 'jpaVendorAdapter' or 'entityManagerFactoryInterface' property "
            + "to select a specific EntityManagerFactory interface to proceed with", ex);
      }
    }
  }

  /**
   * Dynamic proxy invocation handler for an {@link EntityManagerFactory},
   * returning a proxy {@link EntityManager} (if necessary) from
   * {@code createEntityManager} methods.
   */
  @SuppressWarnings("serial")
  private static class ManagedEntityManagerFactoryInvocationHandler implements InvocationHandler, Serializable {

    private final AbstractEntityManagerFactoryBean entityManagerFactoryBean;
    private Method invokeMethod = null;

    public ManagedEntityManagerFactoryInvocationHandler(AbstractEntityManagerFactoryBean emfb) {
      this.entityManagerFactoryBean = emfb;
      Method[] methods = AbstractEntityManagerFactoryBean.class.getDeclaredMethods();
      for (Method method : methods) {
        if ("invokeProxyMethod".equals(method.getName())) {
          method.setAccessible(true);
          this.invokeMethod = method;
        }
      }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      switch (method.getName()) {
        case "equals":
          // Only consider equal when proxies are identical.
          return (proxy == args[0]);
        case "hashCode":
          // Use hashCode of EntityManagerFactory proxy.
          return System.identityHashCode(proxy);
        case "unwrap":
          // Handle JPA 2.1 unwrap method - could be a proxy match.
          Class<?> targetClass = (Class<?>) args[0];
          if (targetClass == null) {
            return this.entityManagerFactoryBean.getNativeEntityManagerFactory();
          } else if (targetClass.isInstance(proxy)) {
            return proxy;
          }
          break;
      }
      try {
        args = new Object[] { method, args };
        if (!method.getName().equals("createEntityManager")) {
          return invokeMethod.invoke(this.entityManagerFactoryBean, args);
        } else {
          Object ret = invokeMethod.invoke(this.entityManagerFactoryBean, args);
          EntityManager em = (EntityManager) ret;
          em.setProperty(Constants.TENANT_ID_PROP, ThreadContext.getTenantId());
          return em;
        }
      } catch (InvocationTargetException ex) {
        throw ex.getTargetException();
      } catch (Exception e) {
        throw e;
      }
    }
  }
}
