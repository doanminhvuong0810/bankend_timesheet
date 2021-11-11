package com.example.timesheet.service;

import com.example.timesheet.dto.UserSalary.GetAllUserSalary;
import com.example.timesheet.dto.UserSalary.GetUserSalaryInMounth;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.UserSalary;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.UserRepo;
import com.example.timesheet.repo.UserSalaryRepo;
import lombok.SneakyThrows;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserSalaryServiceImpl implements UserSalaryService{
    @Autowired
    UserSalaryRepo userSalaryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SalaryRepo salaryRepo;

    @SneakyThrows
    @Override
    public List<GetAllUserSalary> getAll(String timeGet) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(timeGet);
            String mounth = String.valueOf(date.getMonth());
            System.out.println(mounth);
            List<UserSalary> userSalaryList = userSalaryRepo.getSalaryDay(date);
            if (userSalaryList.size() == 0) {
                throw new NotFoundException("common.error.not-found");
            } else {
                List<GetAllUserSalary> getAllUserSalaries = new ArrayList<>();
                userSalaryList.forEach(userSalary -> {
                    GetAllUserSalary allUserSalary = new GetAllUserSalary();
                    allUserSalary.setId(userSalary.getId());
                    allUserSalary.setUserName(userRepo.findByIdGetDL(salaryRepo
                            .findByIdSalary(userSalary.getSalaryId()).getUserId()).getName());
                    allUserSalary.setDate(date);
                    allUserSalary.setSalaryDay(userSalary.getSalaryDay());
                    allUserSalary.setTotal(userSalary.getTotal());
                    allUserSalary.setBonus(userSalary.getBonus());
                    getAllUserSalaries.add(allUserSalary);
                });
                return getAllUserSalaries;
            }
            }catch(Exception e){
                throw new RuntimeException(e);
        }

    }

    @Override
    public List<GetUserSalaryInMounth> getUserSalaryInMounths(String userId, Integer month, Integer year) {
        try {
            Salary salary = new Salary();
            salary = salaryRepo.findByUserId(userId);
            if (salary == null) {
                throw new NotFoundException("common.error.not-found");
            }
            List<UserSalary> userSalaryList = userSalaryRepo.getSalaryUserBySalaryId(salary.getId());
            if (userSalaryList.size() == 0) {
                throw new NotFoundException("common.error.not-found");
            } else {
                List<GetUserSalaryInMounth> getUserSalaryInMounths = new ArrayList<>();
                userSalaryList.forEach(userSalary -> {
                    Integer monthSql = userSalary.getDate().getMonth() + 1;
                    Integer yearSql =  userSalary.getDate().getYear() + 1900;
                    System.out.println(monthSql + "" + yearSql);
                    if(  monthSql.equals(month)  && yearSql.equals(year)) {
                        GetUserSalaryInMounth getUserSalaryInMounth = new GetUserSalaryInMounth();
                        getUserSalaryInMounth.setUserName(userRepo.findByIdGetDL(salaryRepo
                                .findByIdSalary(userSalary.getSalaryId()).getUserId()).getName());
                        getUserSalaryInMounth.setMonth(String.valueOf(userSalary.getDate().getDay())+ "-"+ String.valueOf(monthSql) +"-"+String.valueOf(yearSql));
                        getUserSalaryInMounth.setSalaryDay(userSalary.getSalaryDay());
                        getUserSalaryInMounths.add(getUserSalaryInMounth);
                    } else {
                        System.out.println("no phải");
                    }
                });
                return getUserSalaryInMounths;
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
