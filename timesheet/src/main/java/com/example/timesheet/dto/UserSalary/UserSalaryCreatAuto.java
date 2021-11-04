package com.example.timesheet.dto.UserSalary;

import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserSalaryCreatAuto {

    @NotNull
    private String salaryId;

    @Nullable
    private String bonusId;


}
