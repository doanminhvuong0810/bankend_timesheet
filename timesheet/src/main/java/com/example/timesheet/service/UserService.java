package com.example.timesheet.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timesheet.entity.User;
import com.example.timesheet.repo.UserRepo;


@Service
@Transactional
public class UserService implements IUserService{
    @Autowired
	UserRepo userRepo ;
	
	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		 userRepo.save(user);
	}

	@Override
	public void updateUserById(User user) {
		// TODO Auto-generated method stub
		userRepo.save(user);
		
	}

	@Override
	public void deleteUserById(User user) {
		// TODO Auto-generated method stub
		userRepo.delete(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		
		return userRepo.findAll();
	}

	
}
