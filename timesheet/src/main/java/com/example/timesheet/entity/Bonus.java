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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Bonus")
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = Constants.TENANT_ID_COLUMN, contextProperty = Constants.TENANT_ID_PROP, length = Constants.ID_MAX_LENGTH)
@Table(name = "timesheet_bonus", indexes = {
        @Index(name = "idx_TimeSheetBonus_userid", columnList = "userid", unique = false) })
@ToString(includeFieldNames = true, callSuper = true)
public class Bonus extends EntityBase {

    @NotNull
    @Column(name = "user_id", nullable = false, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String userId;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "id")
    private User user;

    @NotNull
    @Column(name = "timeSheetID", nullable = false, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String timeSheetID;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = TimeSheet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "timeSheetID", insertable = false, updatable = false, referencedColumnName = "id")
    private TimeSheet timeSheet;

    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    @Column(name = "tenant_id", nullable = false, length = Constants.ID_MAX_LENGTH, insertable = false, updatable = false)
    private String tenantId;

    @OneToMany(mappedBy = "bonus")
    @Nullable
    private List<UserSalary> userSalaries;


}
