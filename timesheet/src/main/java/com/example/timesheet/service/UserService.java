package com.example.timesheet.service;

import com.example.timesheet.entity.User;

public interface UserService {
    User findByName (String name);
}
