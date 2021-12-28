package com.vti.railway12.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.railway12.entity.Department;
import com.vti.railway12.repository.IDepartmentRepository;
@Service
public class DepartmentServiceImpl implements IDepartmentService {
	
	@Autowired
	IDepartmentRepository iDepartmentRepository;
	@Override
	
	
	public List<Department> getAllDepartments() {
		
		return iDepartmentRepository.findAll();
	}

	@Override
	public Department getDepartmentById(Long id) {
		
		return iDepartmentRepository.getOne(id);
	}

	@Override
	public void createDepartment(Department department) {
		iDepartmentRepository.save(department);
		
	}

	@Override
	public void updateDepartmentById(Department department) {
		Department departmentupdate= iDepartmentRepository.findById(department.getId()).get();
		iDepartmentRepository.save(departmentupdate);
		
	}

	@Override
	public void deleteDepartmentById(Department department) {
		iDepartmentRepository.delete(department);
		
	}

}
