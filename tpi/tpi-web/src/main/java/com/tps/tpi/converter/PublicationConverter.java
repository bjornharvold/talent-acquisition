package com.tps.tpi.converter;

import com.tps.tpi.dao.BiographyDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.Biography;
import com.tps.tpi.model.objects.entity.Publication;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.PublicationDto;
import com.tps.tpi.model.objects.enums.PublicationTypeCd;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Publication entity to a PublicationDto dto
 */
public class PublicationConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(PublicationConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Publication && dto instanceof PublicationDto) {
            Publication entity = (Publication) source;

            ((PublicationDto) dto).setIssueDate(entity.getIssueDate());
            ((PublicationDto) dto).setType(entity.getType().name());

            if (entity.getBiography() != null) {
                ((PublicationDto) dto).setBiography(entity.getBiography().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
        try {
            if (source instanceof PublicationDto && entity instanceof Publication) {
                PublicationDto dto = (PublicationDto) source;

                ((Publication) entity).setIssueDate(dto.getIssueDate());
                ((Publication) entity).setType(PublicationTypeCd.valueOf(dto.getType()));

                if (StringUtils.isNotBlank(dto.getBiography())) {
                    ((Publication) entity).setBiography(biographyDao.get(Biography.class, dto.getBiography()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private BiographyDao biographyDao;
}