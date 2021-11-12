package com.example.timesheet.dto.timesheet;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateTimeSheet {

    @NotNull
    private String id;

    @NotNull
    private String typeTimeSheet;

    @NotNull
    private Integer percent;
}
