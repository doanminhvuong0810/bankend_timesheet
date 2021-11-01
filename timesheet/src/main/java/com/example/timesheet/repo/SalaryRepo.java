package com.example.timesheet.repo;

import com.example.timesheet.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SalaryRepo extends JpaRepository<Salary, String>, JpaSpecificationExecutor<Salary> {

    Salary findByUserId(String userId);

    @Modifying
    int deleteByIdIn(List<String> ids);
}
