package com.example.security.entity;

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
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "HistoryUpdateGroup")
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = Constants.TENANT_ID_COLUMN, contextProperty = Constants.TENANT_ID_PROP, length = Constants.ID_MAX_LENGTH)
@Table(name = "security_historyupdategroup",indexes = {
        @Index(name = "idx_security_date", columnList = "date", unique = false) })
@ToString(includeFieldNames = true, callSuper = true)
public class HistoryUpdateGroup extends EntityBase {

    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    @Column(name = "tenant_id", nullable = false, length = Constants.ID_MAX_LENGTH, insertable = false, updatable = false)
    private String tenantId;

    @Nullable
    @Column(nullable = true, name="date")
    private Date date;

    @Nullable
    @Column(nullable = true, name="methos")
    private String methos;

    @Nullable
    @Column(nullable = true, name="changeby")
    private String changeby;

    @Nullable
    @Column(nullable = true, name="note")
    private String note;

    @NotNull
    @Column(name = "group_id", nullable = false, length = Constants.ID_MAX_LENGTH)
    @Size(max = Constants.ID_MAX_LENGTH, min = Constants.ID_MAX_LENGTH)
    private String groupId;

    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH }, targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false, referencedColumnName = "id")
    private Group group;


}
