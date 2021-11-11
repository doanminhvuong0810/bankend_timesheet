package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.example.gateway.filter.SimpleFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
//import com.example.springbootzuulgatwayproxy.filters.ErrorFilter;
//import com.example.springbootzuulgatwayproxy.filters.PostFilter;
//import com.example.springbootzuulgatwayproxy.filters.PreFilter;
//import com.example.springbootzuulgatwayproxy.filters.RouteFilter

@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
@CrossOrigin
public class ApiGatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  @Bean
  public SimpleFilter simpleFilter() {
    return new SimpleFilter();
  }
//  @Bean
//  public PreFilter preFilter() {
//      return new PreFilter();
//  }
//  @Bean
//  public PostFilter postFilter() {
//      return new PostFilter();
//  }
//  @Bean
//  public ErrorFilter errorFilter() {
//      return new ErrorFilter();
//  }
//  @Bean
//  public RouteFilter routeFilter() {
//      return new RouteFilter();
//  }
}
