//package com.example.timesheet.service;
//
//import com.example.timesheet.entity.Salary;
//import com.example.timesheet.repo.SalaryRepo;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Timer;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.PostConstruct;
//
//
//public class GetAllAndAutoRun {
//
//    @Autowired
//    SalaryRepo salaryRepo;
//
//    public GetAllAndAutoRun() {
//        List<Salary> salaries = salaryRepo.findAll();
//        Timer t = new Timer();
//    }
//
//}
