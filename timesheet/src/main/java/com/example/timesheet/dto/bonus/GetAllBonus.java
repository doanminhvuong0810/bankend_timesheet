package com.example.timesheet.dto.bonus;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetAllBonus {
    @NotNull
    private String typeTimeSheet;

    @NotNull
    private String userName;

    private LocalDateTime createDate;
}
