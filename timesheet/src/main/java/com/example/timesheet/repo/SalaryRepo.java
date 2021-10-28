package com.example.timesheet.repo;

import com.example.timesheet.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SalaryRepo extends JpaRepository<Salary, String>, JpaSpecificationExecutor<Salary> {
}
