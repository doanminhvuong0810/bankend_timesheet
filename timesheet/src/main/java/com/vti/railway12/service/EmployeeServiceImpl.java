package com.vti.railway12.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.vti.railway12.entity.Employee;
import com.vti.railway12.repository.IEmployeeRepository;
import com.vti.railway12.Form.FormSearchAccount;


@Service
public  class EmployeeServiceImpl implements IEmployeeService {
	@Autowired
	IEmployeeRepository employeeRepository;
	
	@Value("${account.page.recordTotals}")
    private int totalRecords;
	
	@Override
	public Page<Employee> getAllAccounts(int page,String column ) {	
		Pageable pageable = PageRequest.of(page,totalRecords ,Sort.by(column).descending());
		return employeeRepository.findAll(pageable);
	}

	@Override
	public Employee getAccountById(Long id) {
		
		return employeeRepository.getOne(id);
	}

	@Override
	public void createAccount(Employee employee) {
		
		employeeRepository.save(employee);
	}

	
	
	@Override
	public void updateAccountById(Employee employee) {
		Employee accountupdate= employeeRepository.findById(employee.getId()).get();
		accountupdate.setFullName(employee.getFullName());
		accountupdate.setUserName(employee.getUserName());
		accountupdate.setEmail(employee.getEmail());
		accountupdate.setPosition(employee.getPosition());
		accountupdate.setDepartment(employee.getDepartment());
		employeeRepository.save(employee);
		
	}

	@Override
	public void deleteAccountById(Employee employee) {
		employeeRepository.delete(employee);
	}

	@Override
	public boolean isAccountExistByEmail(String email) {
		List<Employee> listAccount  = employeeRepository.findByEmail(email);
		if(listAccount.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isAccountExistByUserName(String userName) {
		List<Employee> listAccount  = employeeRepository.findByUserName(userName);
		if(listAccount.size() > 0) {
			return true;
		}
		return false;
	}



	@Override
	public Page<Employee> search(FormSearchAccount account) {
		Pageable pageable = PageRequest.of(account.getPage(), totalRecords, Sort.by("id"));
		return employeeRepository.findAccountByUserNameAndEmailAndFullNameAndPositionAndDepartment(account.getUserName(),
				account.getEmail(), account.getFullName(), account.getPositionID(), account.getDepartmentID(),
				pageable);
	}

	@Override
	public List<Employee> getAll() {
		return employeeRepository.findAll();
		
	}
	



}
