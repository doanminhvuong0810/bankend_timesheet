
package com.example.common.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.example.common.service.JwtService;


public class JwtAuthFilter extends GenericFilterBean {
  private JwtService jwtService;
  
  public JwtAuthFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    try {
      Authentication authentication = jwtService.getAuthentication((HttpServletRequest) servletRequest);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
    } finally {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}
