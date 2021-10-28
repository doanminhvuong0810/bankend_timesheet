package com.example.timesheet.entity;

import com.example.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Salary")
@Table(name = "timesheet_salary")
public class Salary extends EntityBase {

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @NotNull
    @Positive
    @Column(nullable = false, name="salary")
    private Long salary;

}
