package com.vti.railway12.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.vti.railway12.Form.ErrorMessage;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleExceptionHandler(Exception ex , WebRequest request) {
		
		return new ErrorMessage(10000, ex.getLocalizedMessage());
	}
	
	@ExceptionHandler(JsonMappingException.class)
	@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage ToDoException(Exception ex , WebRequest request) {	
		return new ErrorMessage(6969,"Đối tượng không tồn tại");
	}
	
}
