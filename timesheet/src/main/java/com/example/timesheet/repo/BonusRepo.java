package com.example.timesheet.repo;

import com.example.timesheet.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BonusRepo extends JpaRepository<Bonus, String>, JpaSpecificationExecutor<Bonus> {

    @Query("select b from Bonus b where b.user.id=:userID")
    Bonus findByTimeSheetByUserId(String userID);

}
