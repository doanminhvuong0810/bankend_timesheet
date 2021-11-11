package com.example.timesheet.service;


import com.example.timesheet.dto.UserSalary.UserSalaryCreatAuto;
import com.example.timesheet.dto.salary.AddSalaryForUser;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.UserSalary;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.UserSalaryRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;
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

import java.text.DateFormat;
import java.text.ParseException;
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

    public void amountOneDay(Double salary) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Integer count = 0;
        for (int i = 0; i < LocalDate.now().lengthOfMonth(); i++) {
            if (LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().getValue() != DayOfWeek.SATURDAY.getValue()
                    && LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().getValue() != DayOfWeek.SUNDAY.getValue()) {
                count++;
            }
            System.out.println(sdf.format(Date.from(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())) + " "
                    + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().toString()
                    + " i=" + String.valueOf(count));
        }
        System.out.println("Số ngày làm việc trong tháng: " + String.valueOf(count));
        salaryDay = salary / count;
        System.out.println(salaryDay);
    }


//        @Scheduled(fixedRate = 50000)
    @Scheduled(cron = "0 2 0 ? * MON,TUE,WED,THU,FRI")
    public void add2DBJob() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateNow = formatter.format(date);
        System.out.println("zz " + dateNow);
        Date date1 = formatter.parse(dateNow);

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
                String salaryId = salary1.getId();
                List<UserSalary> userSalaryList = userSalaryRepo.getSalaryUserBySalaryId(salaryId);
                Double totalSalary = 0.0;
                if (userSalaryList.size() <= 0) {
                    totalSalary = 0.0;
                } else {
                    String monthNow = String.valueOf(date1.getMonth()+1);
                    String yearNow = String.valueOf(date1.getYear()+1900);
                    String dayNow = String.valueOf(date1.getDate());
                    String dayBeffor = String.valueOf(Integer.valueOf(dayNow)-1);

                    String dateBeffor = yearNow + "-"+ monthNow + "-"+dayBeffor;
                    System.out.println(dateBeffor);
                    for (int j = 0; j < userSalaryList.size(); j++) {
                        UserSalary userSalary = new UserSalary();
                        PropertyUtils.copyProperties(userSalary,userSalaryList.get(j));
                        Date date2 = userSalary.getDate();
                        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        String date3 = formatter.format(date2);
                        if (date3.equals(dateBeffor)) {
                            totalSalary += userSalary.getTotal();
                        }
                    };
                }
                totalSalary += userSalaryCreatAuto.getSalaryDay();
                userSalaryCreatAuto.setTotal(totalSalary);
//                userSalaryCreatAuto.setTotal();
                UserSalary userSalary = new UserSalary();
                userSalary.setDate(new Date());
                BeanUtils.copyProperties(userSalaryCreatAuto, userSalary);
                userSalaryRepo.save(userSalary);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
//    @Scheduled(fixedRate = 50000)
    @Scheduled(cron = "0 2 0 ? * SAT,SUN")
    public void fetchDBJob() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateNow = formatter.format(date);
        System.out.println("zz " + dateNow);
        Date date1 = formatter.parse(dateNow);


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

                List<UserSalary> userSalaryList = userSalaryRepo.getSalaryUserBySalaryId(salary1.getId());
                Double totalSalary = 0.0;
                if (userSalaryList.size() <= 0) {
                    totalSalary = 0.0;
                } else {
                    String monthNow = String.valueOf(date1.getMonth()+1);
                    String yearNow = String.valueOf(date1.getYear()+1900);
                    String dayNow = String.valueOf(date1.getDate());
                    String dayBeffor = String.valueOf(Integer.valueOf(dayNow)-1);

                    String dateBeffor = yearNow + "-"+ monthNow + "-"+dayBeffor;
                    System.out.println(dateBeffor);
                    for (int j = 0; j < userSalaryList.size(); j++) {
                        UserSalary userSalary = new UserSalary();
                        PropertyUtils.copyProperties(userSalary,userSalaryList.get(j));
                        Date date2 = userSalary.getDate();
                        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                        String date3 = formatter.format(date2);
                        if (date3.equals(dateBeffor)) {
                            totalSalary = totalSalary + userSalary.getTotal();
                        }
                    };
                }
                totalSalary += userSalaryCreatAuto.getSalaryDay();
                userSalaryCreatAuto.setTotal(totalSalary);
//                userSalaryCreatAuto.setTotal();
                UserSalary userSalary = new UserSalary();
                userSalary.setDate(new Date());
                BeanUtils.copyProperties(userSalaryCreatAuto, userSalary);
                userSalaryRepo.save(userSalary);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
//
//        log.info("users : {}", users);
    }
}
