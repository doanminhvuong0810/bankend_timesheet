package com.example.common.service;

import java.time.LocalDateTime;

import com.example.common.enums.TokenTypeEnum;

public interface TokenService {
  void saveToken(String userId, TokenTypeEnum tokenType, String value, LocalDateTime affectDate, LocalDateTime expireDate);

  boolean isTokenValid(String userId, String value);
}
