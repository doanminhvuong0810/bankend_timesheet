package com.example.timesheet.dto.bonus;

import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewBonus {

    @Getter
    @Setter
    @NotNull
    private String typeTimeSheet;

    @NotNull
    private String userId;

    @Nullable
    private Integer otHours;

//    @Nullable
//    private Integer moneyBonus;
}
