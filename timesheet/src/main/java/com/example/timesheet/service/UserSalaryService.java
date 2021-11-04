package com.example.timesheet.service;

import com.example.timesheet.dto.UserSalary.GetAllUserSalary;
import com.example.timesheet.dto.UserSalary.GetUserSalaryInMounth;

import java.util.Date;
import java.util.List;

public interface UserSalaryService {
    List<GetAllUserSalary> getAll(String timeGet);
    List<GetUserSalaryInMounth> getUserSalaryInMounths(String userId,Integer month, Integer year);
}
