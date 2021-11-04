package com.example.timesheet.dto.timesheet;

import com.example.common.dto.request.DateRange;
import lombok.Data;

@Data
public class TimeSheetRequest {
    String type;
    DateRange createDate;
    Boolean isActive;
}
