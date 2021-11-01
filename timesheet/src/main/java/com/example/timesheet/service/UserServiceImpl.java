package com.example.timesheet.service;

import com.example.timesheet.entity.User;
import com.example.timesheet.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public User findByName(String name) {
        return userRepo.findByNameIgnoreCase(name);
    }
}
