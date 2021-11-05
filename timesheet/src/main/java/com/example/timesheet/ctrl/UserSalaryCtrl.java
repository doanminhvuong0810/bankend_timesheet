package com.example.timesheet.ctrl;

import com.example.timesheet.dto.UserSalary.GetAllUserSalary;
import com.example.timesheet.dto.UserSalary.GetUserSalaryInMounth;
import com.example.timesheet.service.UserSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/timesheet/usersalary")
public class UserSalaryCtrl {
    @Autowired
    UserSalaryService userSalaryService;

    @GetMapping("get")
    @ResponseBody
    public List<GetAllUserSalary> getAllUserSalaries(@Valid @RequestParam(value = "timeGet") String timeGet){
        return userSalaryService.getAll(timeGet);
    }
    @GetMapping("getuser")
    @ResponseBody
    public List<GetUserSalaryInMounth> getUserSalaryInMounths(@Valid @RequestParam(value = "userId") String userId, @RequestParam(value = "month") Integer month,  @RequestParam(value = "year") Integer year){
        return userSalaryService.getUserSalaryInMounths(userId, month, year);
    }
}
