/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.model.objects.entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2009
 * Time: 3:51:10 PM
 * Responsibility: Super class for all reference data types. Includes a default amount of useful fields.
 */
@MappedSuperclass
public abstract class AbstractReferenceDataEntity extends AbstractEntity {
    private final static Logger log = LoggerFactory.getLogger(AbstractReferenceDataEntity.class);
    private String code;
    private String description;
    private String shortName;
    private String longName;
    private String ref;

    @Field
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Field
    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Field(index = Index.UN_TOKENIZED)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Field
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Field
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("{code='").append(code).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", longName='").append(longName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
