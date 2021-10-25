package com.example.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.security.entity.User;
import com.example.security.entity.UserRoleRel;

/**
 * @author Pham Duc Manh
 *
 */
public interface UserRoleRelRepo extends JpaRepository<UserRoleRel, String>, JpaSpecificationExecutor<UserRoleRel>{
       
//  @Modifying
  @Query(value = "select r from UserRoleRel r where r.user.id=?1")
  List<UserRoleRel> findByIdUser(String id);
    
  @Query(value = "select r from UserRoleRel r where r.user.id=:userId and r.role.id=:roleId")
  UserRoleRel findUserRoleByUserIdAndRoleId(String userId, String roleId);
//  @Query(value = "select  c from CartDetail  c where c.account.username=?1")
  
}
