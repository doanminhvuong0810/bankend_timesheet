package com.example.timesheet.dto.timesheet;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddTypeTimeSheet {
    public static final int TYPE_MAX_LENGTH = 50;
    public static final int TYPE_MIN_LENGTH = 2;

    @Size(max = TYPE_MAX_LENGTH, min = TYPE_MIN_LENGTH)
    @NotNull
    private String typeTimeSheet;

    @NotNull
    private Integer percent;
}
