package com.example.timesheet.dto;

import com.example.common.config.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateSalaryRequest {

    @NotNull
    private Long salary;

    @Size(min = Constants.ID_MAX_LENGTH, max = Constants.ID_MAX_LENGTH)
    private String tenantId;

    @NotNull
    private String username;
}
