package com.example.timesheet.ctrl;


import com.example.common.dto.response.SuccessResponse;
import com.example.timesheet.dto.timesheet.AddTypeTimeSheet;
import com.example.timesheet.dto.timesheet.GetAllTimeSheet;
import com.example.timesheet.dto.timesheet.UpdateTimeSheet;
import com.example.timesheet.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/timesheet/timesheet")
public class TimeSheetCtrl {
    @Autowired
    TimeSheetService timeSheetService;

    @GetMapping("get")
    public List<GetAllTimeSheet> getAllTimeSheets(){
        return timeSheetService.getAll();
    }


    @PostMapping("new")
    @ResponseBody
    public SuccessResponse create(@Valid @RequestBody AddTypeTimeSheet addTypeTimeSheet) {
        timeSheetService.create(addTypeTimeSheet);
        return new SuccessResponse();
    }

    @PostMapping("update")
    @ResponseBody
    public SuccessResponse update(@Valid @RequestBody UpdateTimeSheet updateTimeSheet){
        timeSheetService.update(updateTimeSheet);
        return new SuccessResponse();
    }

    @PutMapping("delete/{id}")
    @ResponseBody
    public SuccessResponse delete(@PathVariable(value = "id") String id) {
        timeSheetService.delete(id);
        return new SuccessResponse();
    }
}
