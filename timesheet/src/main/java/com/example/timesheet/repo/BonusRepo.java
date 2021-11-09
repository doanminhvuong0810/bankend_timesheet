package com.example.timesheet.repo;

import com.example.timesheet.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface BonusRepo extends JpaRepository<Bonus, String>, JpaSpecificationExecutor<Bonus> {

    @Query("select b from Bonus b where b.user.id=:userID and b.timeSheetID=:timeSheetID and b.date=:date")
    Bonus findByTimeSheetByUserIdAAndTimeSheetID(String userID, String timeSheetID, Date date);

    @Query("select b from Bonus b where b.user.id=:userID and b.date=:date")
    List<Bonus> findByTimeSheetByUserIdAAndDate(String userID, Date date);


}
