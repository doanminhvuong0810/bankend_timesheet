package com.example.security.repo;

import com.example.security.entity.HistoryUpdateGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface HistoryGroupRepo extends JpaRepository<HistoryUpdateGroup, String> , JpaSpecificationExecutor<HistoryUpdateGroup> {
}
