package com.example.timesheet.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.micrometer.core.lang.Nullable;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import com.example.common.config.Constants;
import com.example.common.entity.EntityBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Pham Duc Manh
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TimeSheet")
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = Constants.TENANT_ID_COLUMN, contextProperty = Constants.TENANT_ID_PROP, length = Constants.ID_MAX_LENGTH)
@Table(name = "Manager_TimeSheet", indexes = {
        @Index(name = "idx_ManagerTimeSheet_Type", columnList = "typeTimeSheet", unique = false) })
@ToString(includeFieldNames = true, callSuper = true)
public class TimeSheet extends EntityBase  {
    public static final int TYPE_MAX_LENGTH = 50;
    public static final int TYPE_MIN_LENGTH = 2;

    @NotNull
    @Size(max = TYPE_MAX_LENGTH, min = TYPE_MIN_LENGTH)
    @Column(name = "typeTimeSheet", length = TYPE_MAX_LENGTH)
    private String typeTimeSheet;

    @Nullable
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    @Column(name = "tenant_id", nullable = false, length = Constants.ID_MAX_LENGTH, insertable = false, updatable = false)
    private String tenantId;

    @Nullable
    @OneToMany(mappedBy = "timeSheet")
    List<Bonus> bonus;
}
