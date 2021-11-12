package com.example.timesheet.service;

import com.example.timesheet.dto.bonus.FindUserForNew;
import com.example.timesheet.dto.bonus.GetAllBonus;
import com.example.timesheet.dto.bonus.NewBonus;
import com.example.timesheet.dto.bonus.UpdateBonus;
import com.example.timesheet.dto.timesheet.AddTypeTimeSheet;
import com.example.timesheet.entity.Bonus;
import com.example.timesheet.entity.TimeSheet;

import java.util.List;

public interface BonusService {
    Bonus create(NewBonus newBonus);
    List<GetAllBonus> getAll(String timeGet);
    Bonus update(UpdateBonus updateBonus);
    Bonus delete(String id);
    List<FindUserForNew> findForNew(String userName);
}
