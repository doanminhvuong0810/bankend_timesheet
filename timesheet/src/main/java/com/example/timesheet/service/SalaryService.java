package com.example.timesheet.service;

import com.example.timesheet.dto.salary.AddSalaryForUser;
import com.example.timesheet.dto.salary.UpdateSalaryForUser;
import com.example.timesheet.entity.Salary;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SalaryService {
    Salary createSalaryForUser(AddSalaryForUser addSalaryForUser);
    Salary updateSalaryForUser(UpdateSalaryForUser updateSalaryForUser);
    List<Salary> getAll();
}
