package com.example.timesheet.repo;

import com.example.timesheet.entity.TotalSalaryMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TotalSalaryMonthRepo extends JpaRepository<TotalSalaryMonth, String>, JpaSpecificationExecutor<TotalSalaryMonth> {
       TotalSalaryMonth findOneBySalaryId(String salaryId);
       TotalSalaryMonth findOneById(String id);
}
