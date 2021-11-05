package com.example.timesheet.service;

import com.example.timesheet.dto.UserSalary.GetAllUserSalary;
import com.example.timesheet.entity.UserSalary;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.UserRepo;
import com.example.timesheet.repo.UserSalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserSalaryServiceImpl implements UserSalaryService{
    @Autowired
    UserSalaryRepo userSalaryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SalaryRepo salaryRepo;

    @Override
    public List<GetAllUserSalary> getAll(LocalDateTime timeGet) {
        List<UserSalary> userSalaryList = userSalaryRepo.getSalaryDay(timeGet);
        List<GetAllUserSalary> getAllUserSalaries = new ArrayList<>();
        userSalaryList.forEach(userSalary -> {
            GetAllUserSalary allUserSalary = new GetAllUserSalary();
            allUserSalary.setUserName(userRepo.findByIdGetDL(salaryRepo
                                        .findByIdSalary(userSalary.getSalaryId()).getUserId()).getName());
            allUserSalary.setDate(timeGet);
            getAllUserSalaries.add(allUserSalary);
        });
        return  getAllUserSalaries;
    }
}
