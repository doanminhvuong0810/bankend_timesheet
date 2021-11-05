package com.example.timesheet.repo;

import com.example.timesheet.entity.UserSalary;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserSalaryRepo extends JpaRepository<UserSalary, String>, JpaSpecificationExecutor<UserSalary> {

    @Query("select us from UserSalary us where us.createdDate=:dateTime")
    List<UserSalary> getSalaryDay(LocalDateTime dateTime);

}
