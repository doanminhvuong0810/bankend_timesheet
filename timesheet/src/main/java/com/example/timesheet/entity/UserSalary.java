package com.example.timesheet.entity;

import com.example.common.config.Constants;
import com.example.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserSalary")
@Table(name = "timesheet_user_salary")
public class UserSalary extends EntityBase {

    @NotNull
    @Column(name = "salaryId", nullable = false, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String salaryId;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Salary.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "salaryId", insertable = false, updatable = false, referencedColumnName = "id")
    private Salary salary;

    @NotNull
    @Column(nullable = false, name="salary_day")
    private Double salaryDay;

    @Nullable
    @Column(name = "bonusId", nullable = true, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String bonusId;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Bonus.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bonusId", insertable = false, updatable = false, referencedColumnName = "id")
    private Bonus bonus;

    @Nullable
    @Column(nullable = true, name="total")
    private Double total;

    @Nullable
    @Column(nullable = true, name="date")
    private LocalDateTime date = LocalDateTime.now();

}
