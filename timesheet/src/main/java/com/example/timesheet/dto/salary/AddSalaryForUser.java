package com.example.timesheet.dto.salary;

import com.example.common.config.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddSalaryForUser {

    @NotNull
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String userId;
    @NotNull
    private Double salary;
}
