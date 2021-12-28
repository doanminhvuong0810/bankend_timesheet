package com.vti.railway12.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.vti.railway12.Form.FormSearchAccount;
import com.vti.railway12.entity.Employee;

public interface IEmployeeService {
	List<Employee> getAll();
	
	Page<Employee> getAllAccounts(int page, String column);

	Employee getAccountById(Long id);

	void createAccount(Employee employee);

	void updateAccountById(Employee employee);

	void deleteAccountById(Employee employee);

	boolean isAccountExistByEmail(String email);

	boolean isAccountExistByUserName(String userName);

	Page<Employee> search(FormSearchAccount account);

}
