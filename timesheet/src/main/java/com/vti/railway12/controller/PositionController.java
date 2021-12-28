package com.vti.railway12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.railway12.entity.Position;
import com.vti.railway12.service.IPositionService;

@RestController
@RequestMapping("/position")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PositionController {
	
	@Autowired
	IPositionService iPositionService;
	
	@GetMapping("/list")
	ResponseEntity<List<Position>> getListPosition(){
		List<Position> listPosition = iPositionService.getAllPositions();
		return new ResponseEntity<List<Position>> (listPosition, HttpStatus.OK);
	}
	
	@PostMapping("/create")
    public ResponseEntity<?> createPosition(@RequestBody Position position) {
		
		
		iPositionService.createPosition(position);
        return ResponseEntity.ok(("SUCCESS"));
    }
	
	@PostMapping("/update")
    public ResponseEntity<?> updatePositionById(@RequestBody Position position) {
		
		
		iPositionService.updatePositionById(position);
        return ResponseEntity.ok(("SUCCESS"));
    }
	
}