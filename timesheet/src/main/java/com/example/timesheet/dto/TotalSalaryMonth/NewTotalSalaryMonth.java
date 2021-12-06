package com.example.timesheet.dto.TotalSalaryMonth;

import lombok.Data;

import java.time.Month;
import java.time.Year;

@Data
public class NewTotalSalaryMonth {

    private Double totalMonth;

    private Month timeMonth;

    private Year timeYear;

    private String note;

    private Boolean Status;

}
