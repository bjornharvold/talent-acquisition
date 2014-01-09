/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.model.objects.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Feb 5, 2007
 * Time: 1:06:46 AM
 */
public abstract class AbstractDto {
    private final static Logger log = LoggerFactory.getLogger(AbstractDto.class);
    private String id = null;
    private Integer version = null;
    private Date createdDate = null;
    private Date lastUpdate = null;
    private String recordCreatorType;
    private String recordStatusType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdate() {
        if (lastUpdate == null) {
            lastUpdate = new Date(new Date().getTime());
        }
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getRecordCreatorType() {
        return recordCreatorType;
    }

    public void setRecordCreatorType(String recordCreatorType) {
        this.recordCreatorType = recordCreatorType;
    }

    public String getRecordStatusType() {
        return recordStatusType;
    }

    public void setRecordStatusType(String recordStatusType) {
        this.recordStatusType = recordStatusType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id='").append(id).append('\'');
        sb.append(", version=").append(version);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append(", recordCreatorType='").append(recordCreatorType).append('\'');
        sb.append(", recordStatusType='").append(recordStatusType).append('\'');

        return sb.toString();
    }
}
