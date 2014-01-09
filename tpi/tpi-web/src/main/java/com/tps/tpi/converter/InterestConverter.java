package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts an Interest entity to an InterestDto dto
 */
public class InterestConverter extends AbstractReferenceDataConverter {

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
        return entity;
    }
}