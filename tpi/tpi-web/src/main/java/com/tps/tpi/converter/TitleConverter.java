package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.Title;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.TitleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Title entity to a TitleDto dto
 */
public class TitleConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(TitleConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Title && dto instanceof TitleDto) {
            Title entity = (Title) source;

        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {

        if (source instanceof TitleDto && entity instanceof Title) {
            TitleDto dto = (TitleDto) source;

            // we're not going to be creating titles with dtos so this can be empty
        }

        return entity;
    }
}