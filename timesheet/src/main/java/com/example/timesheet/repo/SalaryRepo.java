package com.example.timesheet.repo;

import com.example.timesheet.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SalaryRepo extends JpaRepository<Salary, String>, JpaSpecificationExecutor<Salary> {
    @Query("select s from Salary s where s.userId=:userId")
    Salary findByUserId(String userId);

    @Query("select s from Salary s where s.id=:id")
    Salary findByIdSalary(String id);
}
