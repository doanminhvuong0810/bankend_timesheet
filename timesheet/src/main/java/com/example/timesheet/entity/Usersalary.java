package com.example.timesheet.entity;

import com.example.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserSalary")
@Table(name = "timesheet_user_salary")
public class Usersalary extends EntityBase {

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Salary.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_id", nullable = false)
    private Salary salaryId;

    @NotNull
    @Column(nullable = false, name="salary_day")
    private String salaryDay;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Bouns.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bonus_id", nullable = false)
    private Bouns bonusId;

    @NotNull
    @Column(nullable = false, name="total")
    private Long total;

    @NotNull
    @Column(nullable = false, name="date")
    private LocalDateTime date;

}
