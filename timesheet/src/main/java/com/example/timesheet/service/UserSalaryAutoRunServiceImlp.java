package com.example.timesheet.service;


import com.example.timesheet.dto.UserSalary.UserSalaryCreatAuto;
import com.example.timesheet.dto.salary.AddSalaryForUser;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.UserSalary;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.UserSalaryRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserSalaryAutoRunServiceImlp {
    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private UserSalaryRepo userSalaryRepo;

    Logger log = LoggerFactory.getLogger(UserSalaryAutoRunServiceImlp.class);

    private Double salaryDay;

   public void amountOneDay(Double salary){
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
       Integer count = 0;
        for (int i = 0; i < LocalDate.now().lengthOfMonth(); i++) {
        if (LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().getValue()!= DayOfWeek.SATURDAY.getValue()
                && LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().getValue() != DayOfWeek.SUNDAY.getValue()) {
            count++;
        }
        System.out.println(sdf.format(Date.from(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())) + " "
                + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().toString()
                + " i=" + String.valueOf(count));
    }
         System.out.println("Số ngày làm việc trong tháng: " + String.valueOf(count));
         salaryDay = salary/count;
       System.out.println(salaryDay);
   }


//    @Scheduled(fixedRate = 50000)
    @Scheduled(cron = "0 2 0 ? * MON,TUE,WED,THU,FRI")
    public void add2DBJob() {
        List<Salary> salary = new ArrayList<>();
//        user.setName("user" + new Random().nextInt(374483));
//        dao.save(user);
        salary = salaryRepo.findAll();
        for (int i = 0; i < salary.size(); i++) {
            try {
                System.out.println("fetch service call in " + new Date().toString());
                System.out.println("no of record fetched : " + salary.get(i));
//            System.out.println("add service call in " + new Date().toString());
                Salary salary1 = salary.get(i);
                this.amountOneDay(salary1.getSalary());
                UserSalaryCreatAuto userSalaryCreatAuto = new UserSalaryCreatAuto();
                userSalaryCreatAuto.setSalaryId(salary1.getId());
                userSalaryCreatAuto.setSalaryDay(salaryDay);
                userSalaryCreatAuto.setTotal(salaryDay);
//                userSalaryCreatAuto.setTotal();

                UserSalary userSalary = new UserSalary();
                userSalary.setDate(new Date());
                BeanUtils.copyProperties(userSalaryCreatAuto, userSalary);
                userSalaryRepo.save(userSalary);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @Scheduled(cron = "0 2 0 ? * SAT,SUN")
    public void fetchDBJob() {
        List<Salary> salary = new ArrayList<>();
//        user.setName("user" + new Random().nextInt(374483));
//        dao.save(user);
        salary = salaryRepo.findAll();
        for (int i = 0; i < salary.size(); i++) {
            try {
                System.out.println("fetch service call in " + new Date().toString());
                System.out.println("no of record fetched : " + salary.get(i));
//            System.out.println("add service call in " + new Date().toString());
                Salary salary1 = salary.get(i);
                this.amountOneDay(salary1.getSalary());
                UserSalaryCreatAuto userSalaryCreatAuto = new UserSalaryCreatAuto();
                userSalaryCreatAuto.setSalaryId(salary1.getId());
                userSalaryCreatAuto.setSalaryDay(0.0);
//                userSalaryCreatAuto.setTotal();
                UserSalary userSalary = new UserSalary();
                userSalary.setDate(new Date());
                BeanUtils.copyProperties(userSalaryCreatAuto, userSalary);
                userSalaryRepo.save(userSalary);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
//
//        log.info("users : {}", users);
    }
}
