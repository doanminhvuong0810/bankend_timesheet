package com.example.timesheet.dto.UserSalary;

import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class GetAllUserSalary {
    private String userName;
    private Date date;
    private Double salaryDay;
    @Nullable
    private Double bonusDay;
    @Nullable
    private Double total;
}
