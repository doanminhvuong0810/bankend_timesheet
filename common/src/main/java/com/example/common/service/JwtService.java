package com.example.common.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.example.common.model.CustomUserDetails;

public interface JwtService {
  String generateAccessToken(CustomUserDetails user, LocalDateTime issueDate, LocalDateTime expireDate);
  String generateRefreshToken(CustomUserDetails user, LocalDateTime issueDate, LocalDateTime expireDate);
  Authentication getAuthentication(HttpServletRequest request);
}