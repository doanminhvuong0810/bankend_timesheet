package com.vti.railway12.service;

import java.util.List;

import com.vti.railway12.entity.Department;



public interface IDepartmentService {
	List<Department> getAllDepartments();
	
	Department getDepartmentById(Long id);
	void createDepartment(Department department);
	void updateDepartmentById(Department department);
	void deleteDepartmentById(Department department);
}
