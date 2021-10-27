package com.example.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.security.entity.Group;

@Transactional
@Repository
public interface GroupRepo extends JpaRepository<Group, String>, JpaSpecificationExecutor<Group> {
	Optional<Group> findByNameIgnoreCase(String name);

	@Modifying
	int deleteByIdIn(List<String> ids);

	Page<Group> findByFullTextSearchContains(String filter, Pageable pageable);
}