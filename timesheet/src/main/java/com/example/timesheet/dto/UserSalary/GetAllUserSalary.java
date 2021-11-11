package com.example.timesheet.dto.UserSalary;

import com.example.timesheet.entity.Bonus;
import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class GetAllUserSalary {
    private String id;
    private String userName;
    private Date date;
    private Double salaryDay;
    @Nullable
    private Double bonusDay;
    @Nullable
    private Double total;

    List<Bonus> bonus;
}
