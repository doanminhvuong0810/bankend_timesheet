package com.example.timesheet.dto.salary;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetByUser {
    @NotNull
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String userName;

    @NotNull
    private Double salary;
}
