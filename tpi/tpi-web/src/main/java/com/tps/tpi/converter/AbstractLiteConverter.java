/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.converter;

import com.tps.tpi.dao.GenericDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.lite.AbstractLite;
import org.apache.commons.lang.StringUtils;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 3:28:27 PM
 */
public abstract class AbstractLiteConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(AbstractLiteConverter.class);

    public final Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
        AbstractLite dto = null;
        AbstractEntity entity = null;

        if (source == null) {
            return null;
        }

        try {
            if (source instanceof AbstractEntity) {
                entity = (AbstractEntity) source;
                // check to see if the object already exists
                if (destination == null) {
                    try {
                        dto = (AbstractLite) destClass.newInstance();
                    } catch (InstantiationException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    }
                } else {
                    dto = (AbstractLite) destination;
                }

                // set abstractentity variables
                dto.setId(entity.getId());

                dto = toDto(dto, (AbstractEntity) source, destClass, sourceClass);

                return dto;
            } else if (source instanceof AbstractLite) {
                dto = (AbstractLite) source;
                if (StringUtils.isNotBlank(dto.getId())) {
                    // load up entity from db
                    entity = (AbstractEntity) dao.get(destClass, dto.getId());
                } else if (destination == null) {
                    try {
                        entity = (AbstractEntity) destClass.newInstance();
                    } catch (InstantiationException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    }
                } else {
                    entity = (AbstractEntity) destination;
                }

                entity = toEntity(entity, dto, destClass, sourceClass);

                return entity;
            } else {
                throw new MappingException("Converter CustomConverter used incorrectly. Arguments passed in were: " + destination + " and " + source);
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve permission entity from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }
    }

    /**
     * Needs to be implemented by the extending class
     *
     * @param lite
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected abstract AbstractLite toDto(AbstractLite lite, AbstractEntity source, Class destClass, Class sourceClass);

    /**
     * Needs to be implemented by the extending class
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected abstract AbstractEntity toEntity(AbstractEntity entity, AbstractLite source, Class destClass, Class sourceClass);


    // Spring IoC
    protected Mapper mapper;
    private GenericDao dao;

    public void setDao(GenericDao dao) {
        this.dao = dao;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}