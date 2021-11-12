package com.example.timesheet.service;

import com.example.timesheet.dto.salary.AddSalaryForUser;
import com.example.timesheet.dto.salary.GetAllSalary;
import com.example.timesheet.dto.salary.GetByUser;
import com.example.timesheet.dto.salary.UpdateSalaryForUser;
import com.example.timesheet.dto.user.LoadUserNameForAddSalary;
import com.example.timesheet.entity.Bonus;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.entity.User;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.UserRepo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class SalaryServiceImpl implements  SalaryService{

    @Autowired
    SalaryRepo salaryRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Salary createSalaryForUser(@Valid AddSalaryForUser addSalaryForUser) {
           User user  = userRepo.findByUserName(addSalaryForUser.getUserName());
            if(user == null ) {
                throw new NotFoundException("common.error.not-found");
            }
            String idUser = user.getId();
            Salary salary = salaryRepo.findByUserId(idUser);
            System.out.println(salary);
            if(salary != null ) {
                throw new DuplicateKeyException("common.error.dupplicate");
            } else {
                try {
                Salary s = new Salary();

                PropertyUtils.copyProperties(s, addSalaryForUser);
                s.setUserId(userRepo.findByUserName(addSalaryForUser.getUserName()).getId());
                return salaryRepo.save(s);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    @Override
    public Salary updateSalaryForUser(@Valid UpdateSalaryForUser updateSalaryForUser) {
            Optional<Salary> salary = salaryRepo.findById(updateSalaryForUser.getId());
            System.out.println(salary);
            if (salary.isPresent()) {
                throw new NotFoundException("common.error.not-found");
            } else {
                try {
                    Salary entitySalary = new Salary();
                entitySalary.setSalary(updateSalaryForUser.getSalary());
                PropertyUtils.copyProperties(salary,entitySalary );
//                entityBonus.setDeleted(false);
                return salaryRepo.save(entitySalary);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    @Override
    public List<GetAllSalary> getAll() {
        try {
            List<Salary> salaries = salaryRepo.findAll();
            List<GetAllSalary> getall = new ArrayList<>();
            salaries.forEach(s ->{
                GetAllSalary allSalary = new GetAllSalary();
                allSalary.setId(s.getId());
                allSalary.setUserId(s.getUserId());
                allSalary.setUserName(s.getUser().getName());
                allSalary.setSalary(s.getSalary());
                getall.add(allSalary);
            });
            return getall;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GetByUser getByUser(String id) {
        try {
            Salary salaries = salaryRepo.findByUserId(id);
            if (salaries == null) {
                throw new NotFoundException("common.error.not-found");
            }
            GetByUser getall = new GetByUser();
            getall.setId(salaries.getId());
            getall.setUserId(salaries.getUserId());
            getall.setUserName(salaries.getUser().getName());
            getall.setSalary(salaries.getSalary());
            return getall;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LoadUserNameForAddSalary> getByUsername() {
       List<User> users = userRepo.findAll();
       List<LoadUserNameForAddSalary> loadUserNameForAddSalaries = new ArrayList<>();
       users.forEach(user -> {
           if(salaryRepo.findByUserId(user.getId()) == null){
               LoadUserNameForAddSalary loadUserNameForAddSalary = new LoadUserNameForAddSalary();
               loadUserNameForAddSalary.setUserName(user.getName());
               loadUserNameForAddSalaries.add(loadUserNameForAddSalary);

           };
       });
        if(loadUserNameForAddSalaries.size() == 0){
            LoadUserNameForAddSalary loadUserNameForAddSalary = new LoadUserNameForAddSalary();
            loadUserNameForAddSalary.setUserName("Không có user nào chưa được thêm lương");
            loadUserNameForAddSalaries.add(loadUserNameForAddSalary);
        }
       return  loadUserNameForAddSalaries;
    }
}
