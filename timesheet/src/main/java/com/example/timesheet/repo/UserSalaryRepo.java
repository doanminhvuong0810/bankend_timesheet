package com.example.timesheet.repo;

import com.example.timesheet.entity.UserSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserSalaryRepo extends JpaRepository<UserSalary, String>, JpaSpecificationExecutor<UserSalary> {
}
