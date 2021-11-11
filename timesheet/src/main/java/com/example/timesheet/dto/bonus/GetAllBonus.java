package com.example.timesheet.dto.bonus;

import com.example.common.config.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class GetAllBonus {
    private String id;

    private String typeTimeSheet;

    private String userId;

    private Date date;

    private String userName;

    private  Integer otHours;

    private  Double moneyBonus;

    private  Double totalBonus;

    private String userSalaryId;
}
