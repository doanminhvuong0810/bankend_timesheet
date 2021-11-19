package com.example.timesheet.service;

import com.example.timesheet.dto.salary.*;
import com.example.timesheet.dto.user.LoadUserNameForAddSalary;
import com.example.timesheet.entity.Salary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface SalaryService {
    Salary createSalaryForUser(AddSalaryForUser addSalaryForUser);

    Salary updateSalaryForUser(UpdateSalaryForUser updateSalaryForUser);

    List<GetAllSalary> getAll();

    GetByUser getByUser(String id);

    List<LoadUserNameForAddSalary> getByUsername();

    List<FindBonusForUsername> getByUsernameFind(String username);

    List<FindBonusFordisplayName> getByDisplayNameFind(String displayname);
}
