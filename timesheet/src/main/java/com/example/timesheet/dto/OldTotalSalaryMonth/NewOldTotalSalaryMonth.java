package com.example.timesheet.dto.OldTotalSalaryMonth;

import lombok.Data;

import java.time.Month;
import java.time.Year;

@Data
public class NewOldTotalSalaryMonth {

    private String id;

    private Double totalMonth;

    private Month timeMonth;

    private Year timeYear;

    private String note;

    private Boolean Status;
}
