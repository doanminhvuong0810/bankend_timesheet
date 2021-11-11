package com.example.timesheet.service;

import com.example.timesheet.dto.bonus.GetAllBonus;
import com.example.timesheet.dto.bonus.NewBonus;
import com.example.timesheet.dto.bonus.UpdateBonus;
import com.example.timesheet.entity.Bonus;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.entity.User;
import com.example.timesheet.entity.UserSalary;
import com.example.timesheet.repo.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BonusServiceImpl implements BonusService {

    @Autowired
    BonusRepo bonusRepo;

    @Autowired
    TimeSheetRepo timeSheetRepo;

    @Autowired
    UserSalaryRepo userSalaryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SalaryRepo salaryRepo;

    @Autowired
    UserSalaryAutoRunServiceImlp userSalaryAutoRunServiceImlp;

    private Double salaryBonus;

    public void bonusCount(Integer bonusHours, Integer percent, Double salaryDay) {
        if (percent > 0) {
            salaryBonus = (((salaryDay / 8) / 100) * percent) * bonusHours;
            System.out.println(salaryBonus);
        } else {
            salaryBonus = 0.0;
        }
    }

    private Double salaryDayInMonth;

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
        salaryDayInMonth = salary / count;
        System.out.println(salaryDayInMonth);
    }

    @Override
    public Bonus create(@Valid NewBonus newBonus) {
        try {
//                String dateNew = String.valueOf();
//                System.out.println(dateNew);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String dateNow = formatter.format(date);
            System.out.println("zz " + dateNow);
            Date date1 = formatter.parse(dateNow);
            String userId = newBonus.getUserId();
            TimeSheet idTimeSheet = timeSheetRepo.findOneBytypeTimeSheet(newBonus.getTypeTimeSheet());
            if (idTimeSheet == null) {
                throw new NotFoundException("common.error.not-found" + newBonus.getTypeTimeSheet());
            }
            Bonus bonus = bonusRepo.findByTimeSheetByUserIdAAndTimeSheetID(userId, idTimeSheet.getId(), date1);
            if (bonus != null) {
                throw new DuplicateKeyException("common.error.dupplicate" + bonus.getUserId() + bonus.getTimeSheetID());
            } else {
                // viet cong thuong
                User user = new User();
                user = userRepo.findByIdGetDL(newBonus.getUserId());
                if (user == null) {
                    throw new NotFoundException("common.error.not-found" + newBonus.getUserId());
                }
                String salaryId = salaryRepo.findByUserId(user.getId()).getId();

                UserSalary userSalary = userSalaryRepo.getUserSalaryByDateAndSalaryId(date1, salaryId);
                if (userSalary == null) {
                    throw new NotFoundException("common.error.not-found");
                }

                System.out.println(userSalary);
                System.out.println(date);
                System.out.println(salaryId);
                Bonus b = new Bonus();
                if (newBonus.getTypeTimeSheet().equals("On Leave")) {
                    Double totalB = 0.0;
                    List<Bonus> bonus1 = new ArrayList<>();
                    bonus1 = bonusRepo.findByTimeSheetByUserIdAAndDate(userId, date1);
//                        bonus1.forEach(bonus2 -> {
//                            totalB = totalB + bonus2.getTotalBonus();
//                            return totalB;
//                        });
                    if (bonus1.size() <= 0) {
                        totalB = 0.0;
                    } else {
                        for (int i = 0; i < bonus1.size(); i++) {
                            totalB += bonus1.get(i).getTotalBonus();
                        }
                    }
                    System.out.println(totalB);
                    userSalary.setSalaryDay(0.0);
                    userSalary.setTotal(totalB);
                    userSalaryRepo.save(userSalary);
                    PropertyUtils.copyProperties(b, newBonus);
                    TimeSheet timeSheet = timeSheetRepo.findOneBytypeTimeSheet(newBonus.getTypeTimeSheet());
                    b.setTimeSheetID(timeSheet.getId());
                    b.setDate(date1);
                    b.setTotalBonus(totalB);
                    b.setUserSalaryId(userSalary.getId());
                    System.out.println(b);
                    newBonus.setOtHours(0);
                    newBonus.setMoneyBonus(0);
                    return bonusRepo.save(b);
                } else if (newBonus.getMoneyBonus() >= 0 && newBonus.getOtHours() == null) {
                    Double salaryDay = userSalary.getSalaryDay();
                    Double totalB = 0.0;
                    List<Bonus> bonus1 = new ArrayList<>();
                    bonus1 = bonusRepo.findByTimeSheetByUserIdAAndDate(userId, date1);
                    if (bonus1.size() <= 0) {
                        totalB = 0.0;
                    } else {
                        for (int i = 0; i < bonus1.size(); i++) {
                            totalB += bonus1.get(i).getTotalBonus();
                        }
                    }
                    Double tolalBonuss = newBonus.getMoneyBonus() + totalB;
                    userSalary.setTotal(userSalary.getTotal() + tolalBonuss);
                    b.setTotalBonus(tolalBonuss);
                    System.out.println(totalB);
                    userSalary.setSalaryDay(salaryDay);
                    userSalaryRepo.save(userSalary);
                    PropertyUtils.copyProperties(b, newBonus);
                    TimeSheet timeSheet = timeSheetRepo.findOneBytypeTimeSheet(newBonus.getTypeTimeSheet());
                    b.setTimeSheetID(timeSheet.getId());
                    b.setDate(date1);
                    b.setUserSalaryId(userSalary.getId());
                    return bonusRepo.save(b);
                } else if (newBonus.getOtHours() >= 0 && newBonus.getMoneyBonus() == null) {
                    Double salaryDay = userSalary.getSalaryDay();
                    Integer percent = timeSheetRepo.findOneBytypeTimeSheet(newBonus.getTypeTimeSheet()).getPercent();
                    System.out.println(percent);
                    Integer otHours = newBonus.getOtHours();
//                    if (newBonus.getTypeTimeSheet().equals("Over Time")) {
                    Double totalB = 0.0;
                    b.setOtHours(otHours);
                    if (salaryDay == 0) {
                        this.amountOneDay(salaryRepo.findByUserId(userId).getSalary());
                        this.bonusCount(otHours, percent, salaryDayInMonth);
                        List<Bonus> bonus1 = new ArrayList<>();
                        bonus1 = bonusRepo.findByTimeSheetByUserIdAAndDate(userId, date1);
//                        bonus1.forEach(bonus2 -> {
//                            totalB = totalB + bonus2.getTotalBonus();
//                            return totalB;
//                        });
                        if (bonus1.size() <= 0) {
                            totalB = 0.0;
                        } else {
                            for (int i = 0; i < bonus1.size(); i++) {
                                totalB += bonus1.get(i).getTotalBonus();
                            }
                        }
                        System.out.println(salaryDayInMonth + "--" + totalB + "-- " + salaryBonus);
                        Double tolalBonuss = salaryBonus + totalB;
                        userSalary.setTotal(userSalary.getTotal() + tolalBonuss);
                        b.setTotalBonus(tolalBonuss); // salaryBonus
                    } else if (salaryDay > 0) {
                        this.bonusCount(otHours, percent, salaryDay);
//
                        List<Bonus> bonus1 = new ArrayList<>();
                        bonus1 = bonusRepo.findByTimeSheetByUserIdAAndDate(userId, date1);
                        if (bonus1.size() <= 0) {
                            totalB = 0.0;
                        } else {
                            for (int i = 0; i < bonus1.size(); i++) {
                                totalB += bonus1.get(i).getTotalBonus();
                            }
                        }
                        Double tolalBonuss = salaryBonus + totalB;
                        userSalary.setTotal(userSalary.getTotal() + tolalBonuss);
                        b.setTotalBonus(tolalBonuss); // salaryBonus
                    }
                    System.out.println(totalB);
                    userSalary.setSalaryDay(salaryDay);
                    userSalaryRepo.save(userSalary);
                    PropertyUtils.copyProperties(b, newBonus);
                    TimeSheet timeSheet = timeSheetRepo.findOneBytypeTimeSheet(newBonus.getTypeTimeSheet());
                    b.setTimeSheetID(timeSheet.getId());
                    b.setDate(date1);
                    b.setUserSalaryId(userSalary.getId());
                    return bonusRepo.save(b);
                }
//                 else if(newBonus.getMoneyBonus() >= 0 && newBonus.getOtHours() >= 0){
//                     throw new DuplicateKeyException("common.error.dupplicate");
//                 }
                else {
                    throw new DuplicateKeyException("common.error.dupplicate");
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GetAllBonus> getAll(String timeGet) {
        try {

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(timeGet);
//            String date1 = String.valueOf(date);

            List<Bonus> bonuses = new ArrayList<>();
            bonuses = bonusRepo.findByDate(date);
            List<GetAllBonus> getAllBonuses = new ArrayList<>();
            bonuses.forEach(bonus -> {
                GetAllBonus getAllBonus = new GetAllBonus();
                getAllBonus.setId(bonus.getId());
                getAllBonus.setTypeTimeSheet(timeSheetRepo.findTimeSheetById(bonus.getTimeSheetID()).getTypeTimeSheet());
                getAllBonus.setUserId(bonus.getUserId());
                getAllBonus.setDate(bonus.getDate());
                getAllBonus.setUserName(userRepo.findByIdGetDL(bonus.getUserId()).getName());
                if(bonus.getOtHours() == null){
                    getAllBonus.setOtHours(0);
                } else {
                    getAllBonus.setOtHours(bonus.getOtHours());
                }
                if(bonus.getMoneyBonus() == null){
                    getAllBonus.setMoneyBonus(0.0);
                } else {
                    getAllBonus.setMoneyBonus(bonus.getMoneyBonus());
                }
                getAllBonus.setUserSalaryId(bonus.getUserSalaryId());
                getAllBonus.setTotalBonus(bonus.getTotalBonus());
                getAllBonuses.add(getAllBonus);
            });
            return getAllBonuses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bonus update(@Valid UpdateBonus updateBonus) {
        try {
            Optional<Bonus> optionalBonus = bonusRepo.findById(updateBonus.getId());
            if (optionalBonus.isPresent()) {
                Bonus entityBonus = new Bonus();
                BeanUtils.copyProperties(entityBonus, optionalBonus);
//                entityBonus.setDeleted(false);
                bonusRepo.save(entityBonus);
                return entityBonus;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bonus delete(String id) {
        try {
            Optional<Bonus> optionalBonus = bonusRepo.findById(id);
            if (optionalBonus.isPresent()) {
                Bonus entityBonus = new Bonus();
                BeanUtils.copyProperties(entityBonus, optionalBonus);
                entityBonus.setDeleted(false);
                bonusRepo.save(entityBonus);
                return entityBonus;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
