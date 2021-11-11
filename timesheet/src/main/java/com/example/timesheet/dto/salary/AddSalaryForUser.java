package com.example.timesheet.dto.salary;

import com.example.common.config.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddSalaryForUser {

    @NotNull
    private String userName;
    @NotNull
    private Double salary;
}
