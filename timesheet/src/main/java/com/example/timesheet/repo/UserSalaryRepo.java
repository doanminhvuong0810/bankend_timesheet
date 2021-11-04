package com.example.timesheet.repo;

import com.example.timesheet.entity.UserSalary;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface UserSalaryRepo extends JpaRepository<UserSalary, String>, JpaSpecificationExecutor<UserSalary> {

    @Query("select us from UserSalary us where us.date=:date")
    List<UserSalary> getSalaryDay(@Param("date") Date date);



//    @Query(nativeQuery = true, value="select * from timesheet_user_salary u where u.salaryId=:salaryId and MONTH(u.date)=:month")
    // and SubString(cast(u.date as text),1,4)=:month
    @Query("select u from UserSalary u where u.salaryId=:salaryId")
    List<UserSalary> getSalaryUserAndMon(@Param("salaryId") String salaryId);//, @Param("month") Integer month);

}
