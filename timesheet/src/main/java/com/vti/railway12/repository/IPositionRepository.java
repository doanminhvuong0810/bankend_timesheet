package com.vti.railway12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vti.railway12.entity.Position;

@Repository
public interface IPositionRepository extends JpaRepository<Position, Long>{
	
}
