package com.example.timesheet.service;

import com.example.timesheet.dto.UserSalary.GetAllUserSalary;
import com.example.timesheet.dto.UserSalary.TimeGet;

import java.time.LocalDateTime;
import java.util.List;

public interface UserSalaryService {
    List<GetAllUserSalary> getAll(LocalDateTime timeGet);
}
