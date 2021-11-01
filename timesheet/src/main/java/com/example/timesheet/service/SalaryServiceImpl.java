package com.example.timesheet.service;

import com.example.timesheet.dto.AddSalaryRequest;
import com.example.timesheet.dto.UpdateSalaryRequest;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.User;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.UserRepo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    @Override
    public Salary save(@Valid AddSalaryRequest salary) {
        User user = userRepo.findByNameIgnoreCase(salary.getUsername());
        Salary s = salaryRepo.findByUserId(user.getId());
        if (s != null) {
            throw new DuplicateKeyException("common.error.dupplicate");
        } else {
            s = new Salary();
            s.setSalary(salary.getSalary());
            s.setUserId(user);
            return salaryRepo.save(s);
        }
    }

    @Override
    public Salary updateAllFields(String id, @Valid UpdateSalaryRequest salary) {
        Optional<Salary> oSalary = salaryRepo.findById(id);
        User user = userRepo.findByNameIgnoreCase(salary.getUsername());
        Salary s = salaryRepo.findByUserId(user.getId());
        if (oSalary.isEmpty()) {
            throw new NotFoundException("common.error.not-found");
        } else if (s != null) {
            throw new DuplicateKeyException("common.error.dupplicate");
        } else {
            try {
                s = new Salary();
                PropertyUtils.copyProperties(s, salary);
                s.setUserId(user);
                return salaryRepo.save(s);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteById(String id) {
        Optional<Salary> oSalary = salaryRepo.findById(id);
        if (oSalary.isEmpty()) {
            throw new NotFoundException("common.error.not-found");
        } else {
            salaryRepo.deleteById(id);
        }
    }

    @Override
    public int deleteByIdIn(List<String> ids) {
        int count = salaryRepo.deleteByIdIn(ids);
        if (count < ids.size()) {
            throw new NotFoundException("common.error.not-found");
        }
        return count;
    }

    @Override
    public Salary setActive(String id, boolean isActive) {
        Optional<Salary> oSalary = salaryRepo.findById(id);
        if (oSalary.isEmpty()) {
            throw new NotFoundException("common.error.not-found");
        }
        oSalary.get().setActive(isActive);
        return salaryRepo.save(oSalary.get());
    }

    @Override
    public Page<Salary> findAll(Pageable pageable) {
        return salaryRepo.findAll(pageable);
    }

    @Override
    public Optional<Salary> findById(String id) {
        return salaryRepo.findById(id);
    }
}
