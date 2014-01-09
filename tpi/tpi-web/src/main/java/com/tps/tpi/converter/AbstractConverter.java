/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.converter;

import com.tps.tpi.dao.GenericDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.enums.RecordCreatorTypeCd;
import com.tps.tpi.model.objects.enums.RecordStatusTypeCd;
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
public abstract class AbstractConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(AbstractConverter.class);

    public final Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
        AbstractDto dto = null;
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
                        dto = (AbstractDto) destClass.newInstance();
                    } catch (InstantiationException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    }
                } else {
                    dto = (AbstractDto) destination;
                }

                // set abstractentity variables
                dto.setId(entity.getId());
                dto.setCreatedDate(entity.getCreatedDate());
                dto.setLastUpdate(entity.getLastUpdate());
                dto.setVersion(entity.getVersion());
                dto.setRecordCreatorType(entity.getRecordCreatorType().name());
                dto.setRecordStatusType(entity.getRecordStatusType().name());

                dto = toDto(dto, (AbstractEntity) source, destClass, sourceClass);

                // flatten entity here
                dto.setId(entity.getId());

                return dto;
            } else if (source instanceof AbstractDto) {
                dto = (AbstractDto) source;
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

                if (StringUtils.isNotBlank(dto.getRecordCreatorType())) {
                    entity.setRecordCreatorType(RecordCreatorTypeCd.valueOf(dto.getRecordCreatorType()));
                }
                if (StringUtils.isNotBlank(dto.getRecordStatusType())) {
                    entity.setRecordStatusType(RecordStatusTypeCd.valueOf(dto.getRecordStatusType()));
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
     * @param dto
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected abstract AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass);

    /**
     * Needs to be implemented by the extending class
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected abstract AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass);


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
