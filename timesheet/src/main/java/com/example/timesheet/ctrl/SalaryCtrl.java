package com.example.timesheet.ctrl;

import com.example.common.dto.response.InsertSuccessResponse;
import com.example.common.dto.response.SuccessResponse;
import com.example.timesheet.common.Constant;
import com.example.timesheet.dto.salary.*;
import com.example.timesheet.dto.timesheet.AddTypeTimeSheet;
import com.example.timesheet.dto.user.LoadUserNameForAddSalary;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.service.SalaryService;
import com.example.timesheet.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(Constant.CROSS_ORIGIN)
@RequestMapping("/api/v1/timesheet/salary")
public class SalaryCtrl {
    @Autowired
    SalaryService salaryService;

    @GetMapping("get")
    public List<GetAllSalary> getAllSalary(){
        return salaryService.getAll();
    }
    @GetMapping("getusername")
    public List<LoadUserNameForAddSalary> getByUsername(){
        return salaryService.getByUsername();
    }

    @GetMapping("getbyuser/{id}")
    public GetByUser getByUserId(@PathVariable(value = "id") String id){
        return salaryService.getByUser(id);
    }

    @GetMapping("getbyusername/{username}")
    public List<FindBonusForUsername> getByUsernameFind(@PathVariable(value = "username") String username){
        return salaryService.getByUsernameFind(username);
    }


    @PostMapping("new")
    @ResponseBody
    public InsertSuccessResponse newSalary(@Valid @RequestBody AddSalaryForUser addSalaryForUser) {
        Salary s = salaryService.createSalaryForUser(addSalaryForUser);
        return new  InsertSuccessResponse(s.getId());
    }
    @PutMapping("update")
    @ResponseBody
    public SuccessResponse updateSalary(@Valid @RequestBody UpdateSalaryForUser updateSalaryForUser) throws NotFoundException {
        salaryService.updateSalaryForUser(updateSalaryForUser);
        return new SuccessResponse();
    }
}
