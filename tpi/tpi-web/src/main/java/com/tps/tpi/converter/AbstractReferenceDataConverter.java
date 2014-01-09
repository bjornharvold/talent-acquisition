/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.converter;

import com.tps.tpi.dao.GenericDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
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
public abstract class AbstractReferenceDataConverter implements CustomConverter {
    private final static Logger log = LoggerFactory.getLogger(AbstractReferenceDataConverter.class);

    @Override
    public final Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
        AbstractReferenceDataDto dto = null;
        AbstractReferenceDataEntity entity = null;

        if (source == null) {
            return null;
        }

        try {
            if (source instanceof AbstractEntity) {
                entity = (AbstractReferenceDataEntity) source;
                // check to see if the object already exists
                if (destination == null) {
                    try {
                        dto = (AbstractReferenceDataDto) destClass.newInstance();
                    } catch (InstantiationException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    }
                } else {
                    dto = (AbstractReferenceDataDto) destination;
                }

                // set abstractentity variables
                dto.setId(entity.getId());
                dto.setCreatedDate(entity.getCreatedDate());
                dto.setLastUpdate(entity.getLastUpdate());
                dto.setVersion(entity.getVersion());
                dto.setShortName(entity.getShortName());
                dto.setLongName(entity.getLongName());
                dto.setCode(entity.getCode());
                dto.setDescription(entity.getDescription());
                dto.setRecordCreatorType(entity.getRecordCreatorType().name());
                dto.setRecordStatusType(entity.getRecordStatusType().name());
                dto.setRef(entity.getRef());

                dto = toDto(dto, (AbstractReferenceDataEntity) source, destClass, sourceClass);

                // flatten entity here
                dto.setId(entity.getId());

                return dto;
            } else if (source instanceof AbstractDto) {
                dto = (AbstractReferenceDataDto) source;
                if (StringUtils.isNotBlank(dto.getId())) {
                    // load up entity from db
                    entity = (AbstractReferenceDataEntity) dao.get(destClass, dto.getId());
                } else if (destination == null) {
                    try {
                        entity = (AbstractReferenceDataEntity) destClass.newInstance();
                    } catch (InstantiationException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error("Cannot instantiate destination class: " + e.getMessage(), e);
                        throw new MappingException("Cannot instantiate destination class: " + e.getMessage(), e);
                    }
                } else {
                    entity = (AbstractReferenceDataEntity) destination;
                }

                entity.setShortName(dto.getShortName());
                entity.setLongName(dto.getLongName());
                entity.setCode(dto.getCode());
                entity.setDescription(dto.getDescription());
                entity.setRecordCreatorType(RecordCreatorTypeCd.valueOf(dto.getRecordCreatorType()));
                entity.setRecordStatusType(RecordStatusTypeCd.valueOf(dto.getRecordStatusType()));
                entity.setRef(dto.getRef());

                entity = toEntity(entity, dto, destClass, sourceClass);

                return entity;
            } else {
                throw new MappingException("Converter CustomConverter used incorrectly. Arguments passed in were: " + destination + " and " + source);
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
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
    protected abstract AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass);

    /**
     * Needs to be implemented by the extending class
     *
     * @param entity
     * @param source
     * @param destClass
     * @param sourceClass
     * @return
     */
    protected abstract AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass);


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