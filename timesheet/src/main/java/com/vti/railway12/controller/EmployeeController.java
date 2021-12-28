package com.vti.railway12.controller;

import java.util.List;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.vti.railway12.Form.FormSearchAccount;
import com.vti.railway12.entity.Employee;
import com.vti.railway12.service.IEmployeeService;

@RestController
@RequestMapping("/employee")
//@CrossOrigin(origins = "http://127.0.0.1:5500")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmployeeController {
	
	@Autowired
	IEmployeeService iEmployeeService;
	
	private static Logger Logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@GetMapping("/get")
//	@PreAuthorize("hasRole('USER') or hasRole('VTI') or hasRole('ADMIN')")
	ResponseEntity<Employee> getAccount(@RequestParam Long id){
		Employee employee = iEmployeeService.getAccountById(id);
		return new ResponseEntity<Employee> (employee, HttpStatus.OK);
	}
	
	@GetMapping("/getall")
    ResponseEntity<List<Employee>> getAll(){
		List<Employee> employee = iEmployeeService.getAll();
		return new ResponseEntity<List<Employee>> (employee, HttpStatus.OK);
	}
	
	@GetMapping("/getlist")
	@PreAuthorize("hasRole('USER') or hasRole('VTI')")
	ResponseEntity<Page<Employee>> getListAccount(@RequestParam int page,@RequestParam String column){
		Page<Employee> listAccount = iEmployeeService.getAllAccounts(page,column);
		List<Employee> employees = listAccount.getContent();
		for (Employee employee : employees) {
			Logger.info("Thông tin employee "+page+ employee.toString());
		}
		return new ResponseEntity<Page<Employee>> (listAccount, HttpStatus.OK);
	}
	
	@PostMapping("/create")
//	@PreAuthorize("hasRole('USER') or hasRole('VTI') or hasRole('ADMIN')")
    public ResponseEntity<?> createAccount(@RequestBody @Valid Employee employee,BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()) {
			throw new Exception(bindingResult.getAllErrors().get(0).getDefaultMessage() );
		}
		
		boolean isExitEmail =iEmployeeService.isAccountExistByEmail(employee.getEmail());
		if(isExitEmail==true) {
			return ResponseEntity.ok("EMAIL");
		}
		boolean isExitUserName =iEmployeeService.isAccountExistByUserName(employee.getUserName());
		if(isExitUserName==true) {
			return ResponseEntity.ok("USERNAME");
		}
		
		iEmployeeService.createAccount(employee);
        return ResponseEntity.ok(("Thêm mới thành công employee:" +employee.getFullName()+"!!!"));
    }
	
	@PostMapping("/update")
//	@PreAuthorize("hasRole('USER') or hasRole('VTI') or hasRole('ADMIN')")
    public ResponseEntity<?> updateAccountById(@RequestBody @Valid Employee employee,BindingResult bindingResult) throws Exception {
		
		if(bindingResult.hasErrors()) {
			throw new Exception(bindingResult.getAllErrors().get(0).getDefaultMessage() );
		}
		
		Employee accountUpdate = iEmployeeService.getAccountById(employee.getId());
		if(!accountUpdate.getEmail().equals(employee.getEmail())) {
			boolean isExistEmail = iEmployeeService.isAccountExistByEmail(employee.getEmail());
			if(isExistEmail) {
				return ResponseEntity.ok(("EMAIL"));
			}
		}
		if(!accountUpdate.getUserName().equals(employee.getUserName())) {
			boolean isExistUserName = iEmployeeService.isAccountExistByUserName(employee.getUserName());
			if(isExistUserName) {
				return ResponseEntity.ok(("USERNAME"));
			}
		}
		
		iEmployeeService.updateAccountById(employee);
        return ResponseEntity.ok(("Sửa thành công employee:" +employee.getFullName()+"!!!"));
    }
	
	
	@PostMapping("/delete")
//	@PreAuthorize("hasRole('USER') or hasRole('VTI') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccountById(@RequestBody Employee employee) {
		
		String fullName = iEmployeeService.getAccountById(employee.getId()).getFullName();
		iEmployeeService.deleteAccountById(employee);
        return ResponseEntity.ok(("Xóa thành công employee:" +fullName+"!!!"));
    }
	
	@PostMapping("/search")
//	@PreAuthorize("hasRole('USER') or hasRole('VTI') or hasRole('ADMIN')")
	public ResponseEntity<?> searchAccount(@RequestBody FormSearchAccount account) {
		Page<Employee> listAccount = iEmployeeService.search(account);
		return new ResponseEntity<Page<Employee>> (listAccount, HttpStatus.OK);
	}
	
}