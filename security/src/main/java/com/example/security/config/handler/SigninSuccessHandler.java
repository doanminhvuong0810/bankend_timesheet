package com.example.security.config.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties.ConfigureAction;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.example.common.model.CustomUserDetails;
import com.example.common.model.SecurityConfigParam;
import com.example.common.service.JwtService;
import com.example.security.dto.user.AuthSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//extends WebSecurityConfigurerAdapter
public class SigninSuccessHandler  implements AuthenticationSuccessHandler {
  private ObjectMapper om = new ObjectMapper();;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private SecurityConfigParam securityParam;
//
//  @Autowired
//  private UserServiceImpl userService;
//   
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    CustomUserDetails authUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    LocalDateTime issueDate = LocalDateTime.now();
    LocalDateTime aExpireDate = issueDate;
    LocalDateTime rExpireDate = issueDate;
    aExpireDate.plusSeconds(securityParam.getAccessTokenExpirationSecond());
    rExpireDate.plusSeconds(securityParam.getRefreshTokenExpirationSecond());
    String authToken = jwtService.generateAccessToken(authUser, issueDate, aExpireDate);
    String refreshToken = jwtService.generateRefreshToken(authUser, issueDate, rExpireDate);

    AuthSuccessResponse ret = new AuthSuccessResponse();
    ret.setAccessToken(authToken);
    ret.setRefreshToken(refreshToken);
    ret.setExpiresIn(aExpireDate);
    
    response.setStatus(HttpServletResponse.SC_OK);
    PrintWriter writer = response.getWriter();
    writer.write(om.writeValueAsString(ret));
  }
  
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth)throws Exception{
//      auth.userDetailsService();
//  }
//  
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    
//    http.cors().disable().csrf().disable();
//    http.authorizeRequests().regexMatchers("/api/v1/users/register").permitAll().anyRequest().authenticated();
//    http.authorizeRequests()
//    .antMatchers("/api/v1/admin/role/").hasAuthority("admin")
//    .antMatchers("/api/v1/users/search/name/get").hasAnyAuthority("admin", "users", "staff");
////    .antMatchers("/api/v1/users/").hasAnyAuthority("users");
// }
}