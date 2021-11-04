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
    private String salaryID;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Salary.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "salaryId", insertable = false, updatable = false, referencedColumnName = "id")
    private Salary salary;

    @NotNull
    @Column(nullable = false, name="salary_day")
    private String salaryDay;

    @Nullable
    @Column(name = "bonusId", nullable = false, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String bonusId;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Bonus.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bonusId", insertable = false, updatable = false, referencedColumnName = "id")
    private Bonus bonus;

    @NotNull
    @Column(nullable = false, name="total")
    private Double total;

    @NotNull
    @Column(nullable = false, name="date")
    private LocalDateTime date;

}
