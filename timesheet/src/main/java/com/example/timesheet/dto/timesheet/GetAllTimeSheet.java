package com.example.timesheet.dto.timesheet;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetAllTimeSheet {
    @NotNull
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String userName;

    

    @NotNull
    private String type;
}
