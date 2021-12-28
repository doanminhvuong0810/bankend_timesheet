package com.vti.railway12.service;

import java.util.List;

import com.vti.railway12.entity.Position;


public interface IPositionService {
List<Position> getAllPositions();
	
	Position getPositionById(Long id);
	void createPosition(Position position);
	void updatePositionById(Position position);
	void deletePositionById(Position position);
}
