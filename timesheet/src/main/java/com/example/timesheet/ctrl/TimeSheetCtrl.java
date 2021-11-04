package com.example.timesheet.ctrl;


import com.example.common.dto.response.SuccessResponse;
import com.example.timesheet.dto.timesheet.AddTypeTimeSheet;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timesheet/timesheet")
public class TimeSheetCtrl {
    @Autowired
    TimeSheetService timeSheetService;

    @GetMapping("get")
    public List<TimeSheet> getAllTimeSheets(){
        return timeSheetService.getAll();
    }


    @PostMapping("new")
    @ResponseBody
    public SuccessResponse newrole(@RequestBody AddTypeTimeSheet addTypeTimeSheet) {
        timeSheetService.create(addTypeTimeSheet);
        return new SuccessResponse();
    }
    @PutMapping("delete/{id}")
    @ResponseBody
    public SuccessResponse newrole(@PathVariable(value = "id") String id) {
        timeSheetService.delete(id);
        return new SuccessResponse();
    }
}
