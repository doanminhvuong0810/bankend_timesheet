package com.example.timesheet.dto.timesheet;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

//import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddTypeTimeSheet {

    @NotNull
    private String typeTimeSheet;
}
