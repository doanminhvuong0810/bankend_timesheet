package com.example.timesheet.ctrl;

import com.example.common.dto.response.SuccessResponse;
import com.example.timesheet.dto.bonus.FindUserForNew;
import com.example.timesheet.dto.bonus.GetAllBonus;
import com.example.timesheet.dto.bonus.NewBonus;
import com.example.timesheet.dto.bonus.UpdateBonus;
import com.example.timesheet.dto.timesheet.AddTypeTimeSheet;
import com.example.timesheet.entity.Bonus;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.service.BonusService;
import com.example.timesheet.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/timesheet/bonus")
public class BonusCtrl {
    @Autowired
    BonusService bonusService;

    @GetMapping("get")
    public List<GetAllBonus> getAllTimeSheets(@RequestParam(value = "timeGet")String timeGet){
        return bonusService.getAll(timeGet);
    }


    @PostMapping("new")
    @ResponseBody
    public SuccessResponse newBonus(@Valid @RequestBody NewBonus newBonus) {
        bonusService.create(newBonus);
        return new SuccessResponse();
    }
    @PutMapping("update")
    @ResponseBody
    public SuccessResponse updateBonus(@Valid @RequestBody UpdateBonus updateBonus) {
        bonusService.update(updateBonus);
        return new SuccessResponse();
    }
    @PutMapping("delete/{id}")
    @ResponseBody
    public SuccessResponse deleteBonus(@PathVariable(value = "id") String id) {
        bonusService.delete(id);
        return new SuccessResponse();
    }
    @GetMapping("getbynew")
    public List<FindUserForNew> findUserForNew(@RequestParam(value = "userName")String userName){
        return bonusService.findForNew(userName);
    }
}
