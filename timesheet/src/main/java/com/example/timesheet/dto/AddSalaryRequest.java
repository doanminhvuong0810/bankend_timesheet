package com.example.timesheet.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddSalaryRequest {

    @NotNull
    private Long salary;

    @NotNull
    private String username;
}
