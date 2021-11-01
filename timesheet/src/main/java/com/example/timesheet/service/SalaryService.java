package com.example.timesheet.service;

import com.example.timesheet.dto.AddSalaryRequest;
import com.example.timesheet.dto.UpdateSalaryRequest;
import com.example.timesheet.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SalaryService {

    Salary save(AddSalaryRequest salary);

    Salary updateAllFields(String id, UpdateSalaryRequest salary);

    void deleteById(String id);

    int deleteByIdIn(List<String> ids);

    Salary setActive(String id, boolean isActive);

    Page<Salary> findAll(Pageable pageable);

    Optional<Salary> findById(String id);
}
