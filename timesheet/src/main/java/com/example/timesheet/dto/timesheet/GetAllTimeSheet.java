package com.example.timesheet.dto.timesheet;

import javax.validation.constraints.NotNull;

public class GetAllTimeSheet {
    @NotNull
    private String id;
    @NotNull
    private String type;
}
