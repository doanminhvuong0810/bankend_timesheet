package com.example.timesheet.repo;

import com.example.timesheet.entity.OldTotalSalaryMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OldTotalSalaryMonthRepo extends JpaRepository<OldTotalSalaryMonth, String>, JpaSpecificationExecutor<OldTotalSalaryMonth> {

}
