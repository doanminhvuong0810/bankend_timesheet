package com.vti.railway12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.railway12.entity.Position;
import com.vti.railway12.repository.IPositionRepository;

@Service
public class PositionServiceImpl implements IPositionService {
	@Autowired
	IPositionRepository iPositionRepository;



	@Override
	public List<Position> getAllPositions() {
		return iPositionRepository.findAll();
	}

	@Override
	public Position getPositionById(Long id) {
		
		return iPositionRepository.getOne(id);
	}

	@Override
	public void createPosition(Position position) {
		iPositionRepository.save(position);
		
	}

	@Override
	public void updatePositionById(Position position) {
		Position positionupdate= iPositionRepository.findById(position.getId()).get();
		iPositionRepository.save(positionupdate);
		
	}

	@Override
	public void deletePositionById(Position position) {
		iPositionRepository.delete(position);
		
	}

}
