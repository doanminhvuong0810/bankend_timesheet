package com.example.timesheet.service;

import com.example.timesheet.dto.bonus.NewBonus;
import com.example.timesheet.dto.bonus.UpdateBonus;
import com.example.timesheet.entity.Bonus;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.entity.User;
import com.example.timesheet.repo.BonusRepo;
import com.example.timesheet.repo.TimeSheetRepo;
import com.example.timesheet.repo.UserRepo;
import com.example.timesheet.repo.UserSalaryRepo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BonusServiceImpl implements BonusService{

    @Autowired
    BonusRepo bonusRepo;

    @Autowired
    TimeSheetRepo timeSheetRepo;

    @Autowired
    UserSalaryRepo userSalaryRepo;

    @Autowired
    UserRepo userRepo;
    @Override
    public Bonus create(@Valid NewBonus newBonus) {
            try {
                String userId = newBonus.getUserId();
                Bonus bonus =  bonusRepo.findByTimeSheetByUserId(userId);
//      if(rol)
                if( bonus != null ) {
                    throw new DuplicateKeyException("common.error.dupplicate");
                } else {
                    User user = new User();
                    user = userRepo.findByIdGetDL(newBonus.getUserId());
                     if(user == null){
                         throw new NotFoundException("common.error.not-found");
                     }

//                    userSalaryRepo

                    Bonus b = new Bonus();
                    PropertyUtils.copyProperties(b, newBonus);
                    TimeSheet timeSheet = timeSheetRepo.findOneBytypeTimeSheet(newBonus.getTypeTimeSheet());
                    b.setTimeSheetID(timeSheet.getId());
                    return bonusRepo.save(b);





                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public List<Bonus> getAll() {
        try {
            List<Bonus> bonuses = new ArrayList<>();
            bonuses = bonusRepo.findAll();
                return bonuses;
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
                BeanUtils.copyProperties(entityBonus,optionalBonus );
//                entityBonus.setDeleted(false);
                bonusRepo.save(entityBonus);
                return entityBonus;
            } else {
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bonus delete(String id) {
        try {
            Optional<Bonus> optionalBonus = bonusRepo.findById(id);
            if (optionalBonus.isPresent()) {
                Bonus entityBonus = new Bonus();
                BeanUtils.copyProperties(entityBonus,optionalBonus);
                entityBonus.setDeleted(false);
                bonusRepo.save(entityBonus);
                return entityBonus;
            } else {
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
