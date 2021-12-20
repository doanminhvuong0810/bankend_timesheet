package com.example.timesheet.service;

import java.util.List;

import com.example.timesheet.entity.User;


public interface IUserService {
	List<User> getAllUsers();
	void create(User user);
	void updateUserById(User user);
	void deleteUserById(User user);
}
