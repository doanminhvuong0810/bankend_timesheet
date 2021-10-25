package com.example.test.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.common.config.enums.SortOrderEnum;
import com.example.common.dto.response.PageResponse;
import com.example.security.entity.User;

@FeignClient("security-service")
public interface SecurityAdapter {
  @RequestMapping(method = RequestMethod.GET, value = "/api/v1/users")
  PageResponse<User> getUsers(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String sort,
      @RequestParam SortOrderEnum order);
}
