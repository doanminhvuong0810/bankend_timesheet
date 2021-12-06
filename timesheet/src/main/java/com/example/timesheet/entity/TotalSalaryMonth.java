package com.example.timesheet.entity;


import com.example.common.config.Constants;
import com.example.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Month;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TotalSalaryMonth")
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = Constants.TENANT_ID_COLUMN, contextProperty = Constants.TENANT_ID_PROP, length = Constants.ID_MAX_LENGTH)
@Table(name = "timesheet_TotalSalaryMonth", indexes = {
        @Index(name = "idx_TimeSheetSalary_timeMonth", columnList = "timeMonth", unique = false) })
public class TotalSalaryMonth extends EntityBase {

    @Nullable
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    @Column(name = "tenant_id", nullable = false, length = Constants.ID_MAX_LENGTH, insertable = false, updatable = false)
    private String tenantId;

    @NotNull
    @Column(name = "salaryId", nullable = false, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String salaryId;

    @JsonIgnore
    @Nullable
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Salary.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "salaryId", insertable = false, updatable = false, referencedColumnName = "id")
    private Salary salary;

    @Nullable
    @Column(nullable = true, name="timeMonth")
    private Integer timeMonth;

    @Nullable
    @Column(nullable = true, name="totalMonth")
    private Double totalMonth;

    @Nullable
    @Column(nullable = true, name="timeYear")
    private Integer timeYear;

    @Nullable
    @Column(nullable = true, name="note")
    private String note;

    @Nullable
    @OneToMany(mappedBy = "totalSalaryMonth")
    List<UserSalary> userSalaries;

    @Nullable
    @Column(nullable = true, name="status")
    private String Status;

    @Nullable
    @Column(nullable = true, name="isDelete")
    private Boolean isDelete;
}
