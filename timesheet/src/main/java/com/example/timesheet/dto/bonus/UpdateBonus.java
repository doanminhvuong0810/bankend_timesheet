package com.example.timesheet.dto.bonus;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class UpdateBonus {
    @NotNull
    @Getter
    private String id;

    @NotNull
    @Getter
    @Setter
    private String typeTimeSheet;

    @NotNull
    @Getter
    private String userName;
}
