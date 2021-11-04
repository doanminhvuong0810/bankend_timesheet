package com.example.security;

import com.example.security.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.example.security.config.SecurityConstants;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Properties;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { SecurityConstants.PACKAGE_BASE })
public class SecurityMicroserviceApplication {

//  DirContext connection;
//  public void newConnection(){
//    Properties env = new Properties();
//    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//    env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
//    env.put(Context.SECURITY_PRINCIPAL, "uid=admin, ou=system");
//    env.put(Context.SECURITY_CREDENTIALS, "secret");
//    try {
//      connection = new InitialDirContext(env);
//      System.out.println("hello world : " + connection);
//    }
//    catch (AuthenticationException exception){
//      System.out.println(exception.getMessage());
//    }
//    catch (NamingException e) {
//      e.printStackTrace();
//    }
//  }

  public static void main(String[] args){
    SpringApplication.run(SecurityMicroserviceApplication.class, args);
//    SecurityMicroserviceApplication runSec = new SecurityMicroserviceApplication();
//      runSec.newConnection();
  }
//   UserServiceImpl userService = new UserServiceImpl().newConnection();


}
