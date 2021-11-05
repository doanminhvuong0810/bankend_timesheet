package com.example.timesheet.service;

import com.example.timesheet.dto.salary.AddSalaryForUser;
import com.example.timesheet.dto.salary.UpdateSalaryForUser;
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

            Salary salary = salaryRepo.findByUserId(addSalaryForUser.getUserId());
            System.out.println(salary);
            if(salary != null ) {
                throw new DuplicateKeyException("common.error.dupplicate");
            } else {
                try {
                Salary s = new Salary();
                PropertyUtils.copyProperties(s, addSalaryForUser);
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
    public List<Salary> getAll() {
        try {
            List<Salary> salaries = new ArrayList<>();
            salaries = salaryRepo.findAll();
            return salaries;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
