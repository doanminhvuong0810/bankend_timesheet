package com.example.timesheet.dto.UserSalary;

import io.micrometer.core.lang.Nullable;
import lombok.Setter;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Setter
public class GetAllUserSalary {
    private String userName;
    private LocalDateTime date;
    private Double salaryDay;
    @Nullable
    private Double bonusDay;
    @Nullable
    private Double total;
}
