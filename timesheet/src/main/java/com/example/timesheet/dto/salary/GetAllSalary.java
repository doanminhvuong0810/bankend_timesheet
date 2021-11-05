package com.example.timesheet.dto.salary;

import com.example.common.config.Constants;
import com.example.timesheet.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class GetAllSalary {

    @NotNull
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String userName;

    @NotNull
    private Double salary;
}
