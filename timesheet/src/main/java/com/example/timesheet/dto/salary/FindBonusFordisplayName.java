package com.example.timesheet.dto.salary;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class FindBonusFordisplayName {
    @NotNull
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String userName;

    @NotNull
    private Double salary;

    private String displayName;

    private String StaffID;

    private String bankName;

    private String bankNumber;

    private Date birthDay;
}
