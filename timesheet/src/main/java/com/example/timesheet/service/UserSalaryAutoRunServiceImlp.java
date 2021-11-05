package com.example.timesheet.service;


import com.example.timesheet.dto.UserSalary.UserSalaryCreatAuto;
import com.example.timesheet.dto.salary.AddSalaryForUser;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.UserSalaryRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserSalaryAutoRunServiceImlp {
    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private UserSalaryRepo userSalaryRepo;

    Logger log = LoggerFactory.getLogger(UserSalaryAutoRunServiceImlp.class);

    // schedule a job to add object in DB (Every 5 sec)
    @Scheduled(fixedRate = 500)
    public void add2DBJob() {
        List<Salary> salary = new ArrayList<>();
//        user.setName("user" + new Random().nextInt(374483));
//        dao.save(user);
        salary = salaryRepo.findAll();
        for (int i = 0; i < salary.size(); i++) {
            System.out.println("add service call in " + new Date().toString());
        }

    }

    @Scheduled(cron = "0 2 12 ? * MON,TUE,WED,THU,FRI *")
    public void fetchDBJob() {
        List<Salary> salaries = salaryRepo.findAll();
        salaries.forEach(salary -> {
            System.out.println("fetch service call in " + new Date().toString());
            System.out.println("no of record fetched : " + salary.getId());
        });

//
//        log.info("users : {}", users);
    }
}
