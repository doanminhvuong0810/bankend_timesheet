package com.example.security.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Role;

/**
 * @author Pham Duc Manh
 *
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role>{
  Role findRoleByName(String name);
//  Optional<Role> findRoleRegister(String name);
}
