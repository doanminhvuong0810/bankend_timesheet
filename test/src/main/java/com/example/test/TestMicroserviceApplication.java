package com.example.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.dto.response.PageResponse;
import com.example.security.entity.User;
import com.example.test.adapter.SecurityAdapter;

@EnableFeignClients
@Controller
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class TestMicroserviceApplication {
  @Autowired
  private SecurityAdapter securityAdapter;

  public static void main(String[] args) {
    SpringApplication.run(TestMicroserviceApplication.class, args);
  }

  @RequestMapping("/test-users")
  @ResponseBody
  public List<User> getUsers() {
    PageResponse<User> ret = securityAdapter.getUsers(0, 5, null, null);
    return ret.getContent();
  }
}
