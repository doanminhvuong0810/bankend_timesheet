package com.example.timesheet.repo;

import com.example.timesheet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByNameIgnoreCase(String name);

    @Query("select u from UserTimesheet u where u.id=:id")
    User findByIdGetDL(String id);
}
