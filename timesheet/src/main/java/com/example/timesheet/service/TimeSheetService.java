package com.example.timesheet.service;

import com.example.timesheet.dto.timesheet.AddTypeTimeSheet;
import com.example.timesheet.dto.timesheet.TimeSheetRequest;
import com.example.timesheet.entity.TimeSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TimeSheetService {
    TimeSheet create(AddTypeTimeSheet addTypeTimeSheet);
    List<TimeSheet> getAll();
    TimeSheet update(AddTypeTimeSheet addTypeTimeSheet);
    TimeSheet delete(String id);
    Page<TimeSheet> advanceSearch(String filter, TimeSheetRequest searchRequest, Pageable pageable);
}
