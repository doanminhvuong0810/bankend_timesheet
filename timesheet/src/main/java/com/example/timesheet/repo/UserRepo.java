package com.example.timesheet.repo;

import com.example.timesheet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByNameIgnoreCase(String name);

    @Query("select u from UserTimesheet u where u.name=:name")
    User findByUserName(String name);

    @Query("select u from UserTimesheet u where u.id=:id")
    User findByIdGetDL(String id);

    @Query("select u from UserTimesheet u where u.name like %:userName%")
    List<User> findForNewBonus(@Param("userName") String userName);

    @Query("select u from UserTimesheet u where u.displayName like %:displayName%")
    List<User> findDSForNewBonus(@Param("displayName") String displayName);

}
