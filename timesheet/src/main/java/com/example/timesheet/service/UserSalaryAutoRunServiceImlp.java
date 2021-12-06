package com.example.timesheet.service;


import com.example.timesheet.dto.UserSalary.UserSalaryCreatAuto;
import com.example.timesheet.dto.salary.AddSalaryForUser;
import com.example.timesheet.entity.OldTotalSalaryMonth;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.TotalSalaryMonth;
import com.example.timesheet.entity.UserSalary;
import com.example.timesheet.repo.OldTotalSalaryMonthRepo;
import com.example.timesheet.repo.SalaryRepo;
import com.example.timesheet.repo.TotalSalaryMonthRepo;
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
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserSalaryAutoRunServiceImlp {
    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private UserSalaryRepo userSalaryRepo;

    @Autowired
    TotalSalaryMonthRepo totalSalaryMonthRepo;

    @Autowired
    OldTotalSalaryMonthRepo oldTotalSalaryMonthRepo;

    Logger log = LoggerFactory.getLogger(UserSalaryAutoRunServiceImlp.class);

    private Double salaryDay;
    private Integer workDays;
    public void getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date
        System.out.println(workDays);
//        return workDays;
    }
    public void amountOneDay(Double salary) throws ParseException {
        //chưa check trường hợp ngày cuối tháng 25 -30
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Integer monthBeffor;
        Integer yearBeffor;
        Integer monthNow = new Date().getMonth() +1;
        Integer yearNow = new Date().getYear() + 1900;
        if(monthNow == 1){
            monthBeffor = 12;
             yearBeffor = new Date().getYear() -1 + 1900;
        }
        else {
            monthBeffor = new Date().getMonth();
             yearBeffor = new Date().getYear() + 1900;
        }
        String startDate =  "25-" + monthBeffor+"-"+yearBeffor;
        String endDate = "25-" + monthNow+"-"+yearNow;
        Date startDate1 = formatter.parse(startDate);
        Date endDate1 = formatter.parse(endDate);
        System.out.println(startDate1 + " ---- " + endDate1);
//        Integer count = 0;
//        for (int i = 0; i < LocalDate.now().lengthOfMonth(); i++) {
//            if (LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().getValue() != DayOfWeek.SATURDAY.getValue()
//                    && LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().getValue() != DayOfWeek.SUNDAY.getValue()) {
//                count++;
//            }
//            System.out.println(sdf.format(Date.from(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1)
//                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())) + " "
//                    + LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), i + 1).getDayOfWeek().toString()
//                    + " i=" + String.valueOf(count));
//        }
//        System.out.println("Số ngày làm việc trong tháng: " + String.valueOf(count));
        this.getWorkingDaysBetweenTwoDates(startDate1,endDate1);
        salaryDay = salary / workDays;
        System.out.println(salaryDay);
    }



//        @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 10 0 ? * MON,TUE,WED,THU,FRI")
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
                userSalaryCreatAuto.setSalaryMonthId(totalSalaryMonthRepo.findOneBySalaryId(salary1.getId()).getId());
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
//                userSalary.setSalaryMonthId(totalSalaryMonthRepo.findOneBySalaryId(salary1.getId()).getId());
                BeanUtils.copyProperties(userSalaryCreatAuto, userSalary);
                userSalaryRepo.save(userSalary);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
//    @Scheduled(fixedRate = 50000)
    @Scheduled(cron = "0 10 0 ? * SAT,SUN")
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
                userSalaryCreatAuto.setSalaryMonthId(totalSalaryMonthRepo.findOneBySalaryId(salary1.getId()).getId());
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
    }
    @Scheduled(cron = "0 2 0 25 1/1 ?")
    public void fetchDBJobInMonth() throws ParseException {
        List<Salary> salary = salaryRepo.findAll();;
//        user.setName("user" + new Random().nextInt(374483));
//        dao.save(user);
        for (int i = 0; i < salary.size(); i++) {
            try {
                Salary salary1 = salary.get(i);

                List<TotalSalaryMonth> totalSalaryMonths = totalSalaryMonthRepo.findAll();
                if(totalSalaryMonths.size() >0) {
                    for (TotalSalaryMonth totalSalaryMonth : totalSalaryMonths) {
                        OldTotalSalaryMonth oldTotalSalaryMonth = new OldTotalSalaryMonth();
                        oldTotalSalaryMonth.setTotalMonth(totalSalaryMonth.getTotalMonth());
                        oldTotalSalaryMonth.setTimeMonth(totalSalaryMonth.getTimeMonth());
                        oldTotalSalaryMonth.setTimeYear(totalSalaryMonth.getTimeYear());
                        oldTotalSalaryMonth.setId(totalSalaryMonth.getId());
                        oldTotalSalaryMonth.setStatus("Chưa thanh toán");
                        oldTotalSalaryMonth.setNote("Note cũ:" + totalSalaryMonth.getNote() + ", Thông tin cũ: createdate: " + totalSalaryMonth.getCreatedDate());
                        oldTotalSalaryMonthRepo.save(oldTotalSalaryMonth);
                        totalSalaryMonthRepo.delete(totalSalaryMonth);
                    }
                }
                Integer month = new Date().getMonth() +1;
                TotalSalaryMonth totalSalaryMonth1 = new TotalSalaryMonth();
                totalSalaryMonth1.setTotalMonth(0.0);
                totalSalaryMonth1.setTimeMonth(month);
                totalSalaryMonth1.setTimeYear(LocalDate.now().getYear());
                totalSalaryMonth1.setStatus("Đang tính");
                totalSalaryMonth1.setNote("Lương tháng "+ LocalDate.now().getMonth() + " năm " + LocalDate.now().getYear());
                totalSalaryMonth1.setSalaryId(salary1.getId());
                totalSalaryMonth1.setIsDelete(false);
                totalSalaryMonthRepo.save(totalSalaryMonth1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
