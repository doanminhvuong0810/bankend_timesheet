package com.example.common.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.common.enums.TokenTypeEnum;
import com.example.common.model.CustomUserDetails;
import com.example.common.model.SecurityConfigParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {
  private static final String SCOPES = "scopes";
  private static final String ENABLED = "enabled";
  private static final String PRINCIPAL = "principal";

  private SecurityConfigParam securityParam;
  private ObjectMapper objectMapper;
  private JwtParser jwtParser;
  private JwtBuilder jwtBuilder;
  private TokenService tokenService = null;

  public JwtServiceImpl(SecurityConfigParam securityParam, TokenService tokenService) {
    this.securityParam = securityParam;
    this.tokenService = tokenService;
    this.objectMapper = new ObjectMapper();
    jwtParser = Jwts.parser().setSigningKey(securityParam.getSecretKey());
    jwtBuilder = Jwts.builder().setIssuer(securityParam.getIssuer()).signWith(SignatureAlgorithm.HS512,
        securityParam.getSecretKey());
  }

  @Override
  public String generateAccessToken(CustomUserDetails user, LocalDateTime issueDate,LocalDateTime expireDate) {
    List<String> authorities = new ArrayList<>();
    for (GrantedAuthority ga : user.getAuthorities()) {
      authorities.add(ga.getAuthority());
    }
    Claims claims = Jwts.claims();
    claims.setSubject(user.getUsername());
    claims.put(SCOPES, authorities);
    claims.put(ENABLED, user.isEnabled());
    claims.put(PRINCIPAL, user);
    // @formatter:off
    Date iDate = Date.from(issueDate.atZone(ZoneId.systemDefault()).toInstant());
    Date eDate = Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant());
    String token = jwtBuilder.setId(UUID.randomUUID().toString()).setIssuedAt(iDate)
        .setExpiration(eDate).setClaims(claims).compact();
    if (tokenService != null) {
      tokenService.saveToken(user.getId(), TokenTypeEnum.AccessToken, token, issueDate, expireDate);
    }
    return token;
    // @formatter:on
  }

  @Override
  public String generateRefreshToken(CustomUserDetails user, LocalDateTime issueDate, LocalDateTime expireDate) {
    Claims claims = Jwts.claims();
    claims.setSubject(user.getUsername());
    claims.put(SCOPES, Collections.singletonList("REFRESH_TOKEN"));
    claims.put(ENABLED, user.isEnabled());
    claims.put(PRINCIPAL, user);
    // @formatter:off
    Date iDate = Date.from(issueDate.atZone(ZoneId.systemDefault()).toInstant());
    Date eDate = Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant());
    String token = jwtBuilder.setId(UUID.randomUUID().toString()).setIssuedAt(iDate)
        .setExpiration(eDate).setClaims(claims).compact();
    if (tokenService != null) {
      tokenService.saveToken(user.getId(), TokenTypeEnum.RefreshToken, token, issueDate, expireDate);
    } 
    return token;
    // @formatter:on
  }

  @Override
  public Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token == null || !token.startsWith(securityParam.getTokenPrefix())) {
      return null;
    }
    token = token.substring(securityParam.getTokenPrefix().length() + 1);
    Claims claims = jwtParser.parseClaimsJws(token).getBody();

    String user = claims.getSubject();
    if (user == null || user.isEmpty()) {
      return null;
    }
    Object authObj = claims.get(PRINCIPAL);
    CustomUserDetails principal = objectMapper.convertValue(authObj, new TypeReference<CustomUserDetails>() {
    });
    principal.setActive((Boolean) claims.get(ENABLED));
    Object scopesObj = claims.get(SCOPES);
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    List<String> scopes = objectMapper.convertValue(scopesObj, new TypeReference<List<String>>() {
    });
    scopes.forEach(scope -> {
      authorities.add(new SimpleGrantedAuthority(scope));
      ;
    });

    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
  }
}
