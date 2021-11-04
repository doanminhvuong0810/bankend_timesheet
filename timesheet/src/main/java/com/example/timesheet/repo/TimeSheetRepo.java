package com.example.timesheet.repo;

import com.example.timesheet.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TimeSheetRepo extends JpaRepository<TimeSheet, String>, JpaSpecificationExecutor<TimeSheet>{
//    Role findRoleByName(String name);
  @Query("select t from TimeSheet t where t.typeTimeSheet=:type")
  Optional<TimeSheet> findBytypeTimeSheet(String type);

  @Query("select t from TimeSheet t where t.typeTimeSheet=:type")
  TimeSheet findOneBytypeTimeSheet(String type);

}

