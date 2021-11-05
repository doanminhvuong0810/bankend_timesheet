package com.example.timesheet.ctrl;

import com.example.timesheet.dto.UserSalary.GetAllUserSalary;
import com.example.timesheet.service.UserSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/timesheet/usersalary")
public class UserSalaryCtrl {
    @Autowired
    UserSalaryService userSalaryService;

    @GetMapping("get")
    @ResponseBody
    public List<GetAllUserSalary> getAllUserSalaries(@Valid @RequestParam(value = "time") LocalDateTime timeGet){
        return userSalaryService.getAll(timeGet);
    }
}
