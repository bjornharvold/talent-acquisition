/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.objects.enums.RecordCreatorTypeCd;
import com.tps.tpi.model.objects.enums.RecordStatusTypeCd;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2009
 * Time: 3:51:10 PM
 * Responsibility: Base entity for all other entity objects
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    private String id = null;
    private Integer version = null;
    private Date createdDate = null;
    private Date lastUpdate = null;
    private RecordCreatorTypeCd recordCreatorType = RecordCreatorTypeCd.ENTERPRISE;
    private RecordStatusTypeCd recordStatusType = RecordStatusTypeCd.ACTIVE;
    private boolean _saved = false;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @DocumentId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Version
    @Column(nullable = false)
    @NotNull
    public Integer getVersion() {

        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Type(type = "recordCreatorType")
    public RecordCreatorTypeCd getRecordCreatorType() {
        return recordCreatorType;
    }

    public void setRecordCreatorType(RecordCreatorTypeCd recordCreatorType) {
        this.recordCreatorType = recordCreatorType;
    }

    @Type(type = "recordStatusType")
    public RecordStatusTypeCd getRecordStatusType() {
        return recordStatusType;
    }

    public void setRecordStatusType(RecordStatusTypeCd recordStatusType) {
        this.recordStatusType = recordStatusType;
    }

    public boolean equals(Object value) {
        boolean result = false;

        /*
        if (log.isTraceEnabled()) {
            log.trace("Running equals on incoming entity: " + value);
        }
        */

        if (this == value) {
            return true;
        }

        if (getId() == null) {
            return false;
        }

        if (value instanceof AbstractEntity) {
            AbstractEntity abo = (AbstractEntity) value;

            /*
            if (log.isTraceEnabled()) {
                log.trace("\nobject: " + getClass().getSimpleName() + ", comparing ids : " + getId() + " and " + abo.getId() +
                          "\n and version: " + getVersion() + " and " + abo.getVersion() +
                          "\n and createddate: " + getCreatedDate() + " and " + abo.getCreatedDate() +
                          "\n and version: " + getLastUpdate() + " and " + abo.getLastUpdate());
            }
            */

            result = new EqualsBuilder()
                    .append(getId(), abo.getId())
                    .append(getVersion(), abo.getVersion())
                    .append(getCreatedDate(), abo.getCreatedDate())
                    .append(getLastUpdate(), abo.getLastUpdate())
                    .isEquals();
        } else {

            /*
            if (log.isTraceEnabled()) {
                log.trace("Objects of different type. Cannot compare: " + getClass().getSimpleName() + " with " + value.getClass().getSimpleName() + "("+value+")");
            }
            */
        }

        /*
        if (log.isTraceEnabled()) {
            log.trace("equals() result : " + result);
        }
        */

        return result;
    }

    public int hashCode() {
        int result = new HashCodeBuilder(17, 37)
                .append(id)
                .append(version)
                .append(createdDate)
                .append(lastUpdate).toHashCode();

        /*
        if (log.isTraceEnabled()) {
            log.trace("hashCode() for object: " + getClass().getSimpleName() + " - " + result);
        }
        */

        return result;
    }


    @Transient
    public void onSave() {
        _saved = true;

        lastUpdate = new Date(new Date().getTime());

        if (version == null) {
            version = 0;
        }

        if (createdDate == null) {
            createdDate = new Date(new Date().getTime());
        }
    }


    @Transient
    public void onLoad() {
        _saved = true;
    }


    @Transient
    public boolean isSaved() {
        return _saved;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" id='").append(id).append('\'');
        sb.append(", version=").append(version);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastUpdate=").append(lastUpdate);
        return sb.toString();
    }
}