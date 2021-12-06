package com.example.timesheet.entity;

import com.example.common.config.Constants;
import com.example.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserSalary")
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = Constants.TENANT_ID_COLUMN, contextProperty = Constants.TENANT_ID_PROP, length = Constants.ID_MAX_LENGTH)
@Table(name = "timesheet_user_salary", indexes = {
        @Index(name = "idx_TimeSheetUserSalary_date", columnList = "date", unique = false) })
public class UserSalary extends EntityBase {
    @Nullable
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    @Column(name = "tenant_id", nullable = false, length = Constants.ID_MAX_LENGTH, insertable = false, updatable = false)
    private String tenantId;
//    @NotNull
//    @Column(name = "salaryId", nullable = false, length = Constants.ID_MAX_LENGTH)
//    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
//    private String salaryId;
//
//    @JsonIgnore
//    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
//            CascadeType.REFRESH }, targetEntity = Salary.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "salaryId", insertable = false, updatable = false, referencedColumnName = "id")
//    private Salary salary;

    @NotNull
    @Column(name = "salaryMonthId", nullable = false, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String salaryMonthId;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = TotalSalaryMonth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "salaryMonthId", insertable = false, updatable = false, referencedColumnName = "id")
    private TotalSalaryMonth totalSalaryMonth;

    @NotNull
    @Column(nullable = false, name="salary_day")
    private Double salaryDay;

    @OneToMany(mappedBy = "userSalary")
    @Nullable
    private List<Bonus> bonus;

    @Nullable
    @Column(nullable = true, name="total")
    private Double total;

    @NotNull
    @Column(nullable = false, name="date")
    private Date date;

    @Nullable
    @Column(nullable = true, name="note")
    private String note;
}
