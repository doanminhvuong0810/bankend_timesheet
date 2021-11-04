package com.example.timesheet.dto.UserSalary;

import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetUserSalaryInMounth {
    private String userName;
    private Integer month;
    private Double salaryDay;
    @Nullable
    private Double bonusDay;
    @Nullable
    private Double total;
}
