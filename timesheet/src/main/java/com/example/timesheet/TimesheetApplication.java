package com.example.timesheet;

import com.example.timesheet.dto.UserSalary.UserSalaryCreatAuto;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.UserSalary;
import com.example.timesheet.repo.SalaryRepo;
//import com.example.timesheet.service.GetAllAndAutoRun;
import com.example.timesheet.service.SalaryService;
//import com.example.timesheet.service.UserSalaryAutoRunServiceImlp;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;


@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class TimesheetApplication {
//
//	DirContext connection;
//	public void newConnection(){
//		Properties env = new Properties();
//		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//		env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
//		env.put(Context.SECURITY_PRINCIPAL, "uid=admin, ou=system");
//		env.put(Context.SECURITY_CREDENTIALS, "secret");
//		try {
//			connection = new InitialDirContext(env);
//			System.out.println("hello world : " + connection);
//		}
//		catch (AuthenticationException exception){
//			System.out.println(exception.getMessage());
//		}
//		catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}
//	public void getAllUsers() throws NamingException {
//		String searchFilter = "(objectClass=inetOrgPerson)";
//		String[] reqAtt = { "cn", "sn" };
//		SearchControls controls = new SearchControls();
//		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//		controls.setReturningAttributes(reqAtt);
//
//		NamingEnumeration users = connection.search("ou=users,ou=system", searchFilter, controls);
//
//		SearchResult result = null;
//		while (users.hasMore()) {
//			result = (SearchResult) users.next();
//			Attributes attr = result.getAttributes();
//			String name = attr.get("cn").get(0).toString();
//			//deleteUserFromGroup(name,"Administrators");
//			System.out.println(attr.get("cn"));
//			System.out.println(attr.get("sn"));
//		}
//	}
//	public void addUser() {
//		Attributes attributes = new BasicAttributes();
//		Attribute attribute = new BasicAttribute("objectClass");
//		attribute.add("inetOrgPerson");
//
//		attributes.put(attribute);
//		// user details
//		attributes.put("sn", "Ricky");
//		try {
//			connection.createSubcontext("cn=Tommy,ou=users,ou=system", attributes);
//			System.out.println("success");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	public void addUserToGroup(String username, String groupName)
//	{
//		ModificationItem[] mods = new ModificationItem[1];
//		Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
//		mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
//		try {
//			connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
//			System.out.println("success");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//	public void deleteUser()
//	{
//		try {
//			connection.destroySubcontext("cn=Tommy,ou=users,ou=system");
//			System.out.println("success");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteUserFromGroup(String username, String groupName)
//	{
//		ModificationItem[] mods = new ModificationItem[1];
//		Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
//		mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
//		try {
//			connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
//			System.out.println("success");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	public void searchUsers() throws NamingException {
//		//String searchFilter = "(uid=1)"; //  for one user
//		//String searchFilter = "(&(uid=1)(cn=Smith))"; // and condition
//		String searchFilter = "(|(uid=1)(uid=2)(cn=Smith))"; // or condition
//		String[] reqAtt = { "cn", "sn","uid" };
//		SearchControls controls = new SearchControls();
//		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//		controls.setReturningAttributes(reqAtt);
//
//		NamingEnumeration users = connection.search("ou=users,ou=system", searchFilter, controls);
//
//		SearchResult result = null;
//		while (users.hasMore()) {
//			result = (SearchResult) users.next();
//			Attributes attr = result.getAttributes();
//			String name = attr.get("cn").get(0).toString();
//			//deleteUserFromGroup(name,"Administrators");
//			System.out.println(attr.get("cn"));
//			System.out.println(attr.get("sn"));
//			System.out.println(attr.get("uid"));
//		}
//
//	}z

//	@Autowired
//	SalaryRepo salaryRepo;

//	@PostConstruct
//	public void testAutoRun(){
//		List<Salary> salaries = salaryRepo.findAll();
//		Timer t = new Timer();
////		for (int i = 0; i < salaries.size(); i++) {
//		salaries.forEach(salary -> {
//			Salary salary1 = new Salary();
//			BeanUtils.copyProperties(salary, salary1);
//			UserSalaryAutoRunServiceImlp t1 = new UserSalaryAutoRunServiceImlp(salary1);
//			t.scheduleAtFixedRate(t1, ChronoUnit.MILLIS.between(LocalDateTime.now(),
//							LocalDateTime.of(LocalDate.now().getYear(),
//									LocalDate.now().getMonth(),
//									LocalDate.now().getDayOfMonth(),
//									23, 59, 59)) + 50 //+ 10
//					, 30000);//24 * 60 * 60 * 1000);
//		});
////		}
//	}
	public static void main(String[] args) throws NamingException {
		SpringApplication.run(TimesheetApplication.class, args);
		//Goi class de insert v.v.
//		TimesheetApplication app = new TimesheetApplication();
//		app.getAllSalry();
//		app.newConnection();
//		app.getAllUsers();
//		List<Salary> salaries = salaryRepo.findAll();
//		Timer t = new Timer();
////		for (int i = 0; i < salaries.size(); i++) {
//		salaries.forEach(salary -> {
//			Salary salary1 = new Salary();
//			BeanUtils.copyProperties(salary, salary1);
//			UserSalaryAutoRunServiceImlp t1 = new UserSalaryAutoRunServiceImlp(salary1);
//			t.scheduleAtFixedRate(t1, ChronoUnit.MILLIS.between(LocalDateTime.now(),
//							LocalDateTime.of(LocalDate.now().getYear(),
//									LocalDate.now().getMonth(),
//									LocalDate.now().getDayOfMonth(),
//									23, 59, 59)) + 50 //+ 10
//					, 30000);//24 * 60 * 60 * 1000);
//		});


//		for (int i = 0; i < 1000000; i++) {
//			employeeIds.add(String.valueOf(i + 1));
//		}
//		List<Salary> salaries = salaryService.getAll();
//		List<UserSalaryCreatAuto> userSalaryCreatAutos = new ArrayList<>();
//		salaries.forEach(salary -> {
//			try {
//				PropertyUtils.copyProperties(salary, userSalaryCreatAutos);
//			}  catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//				throw new RuntimeException(e);
//			}
//		});
//		Timer t = new Timer();
//		for (int i = 0; i < salaries.size(); i++) {
//			UserSalaryAutoRunServiceImlp t1 = new UserSalaryAutoRunServiceImlp(salaries);
//			t.scheduleAtFixedRate(t1, ChronoUnit.MILLIS.between(LocalDateTime.now(),
//							LocalDateTime.of(LocalDate.now().getYear(),
//									LocalDate.now().getMonth(),
//									LocalDate.now().getDayOfMonth(),
//									23, 59, 59)) + i * 50 //+ 10
//					, 30000);//24 * 60 * 60 * 1000);
//		}
	}
}
