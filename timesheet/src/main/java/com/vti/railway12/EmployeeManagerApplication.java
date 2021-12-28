package com.vti.railway12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;





@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })

public class EmployeeManagerApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagerApplication.class, args);
	}

}
