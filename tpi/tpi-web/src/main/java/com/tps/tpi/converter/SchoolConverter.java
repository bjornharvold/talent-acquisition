package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.School;
import com.tps.tpi.model.objects.entity.Address;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.SchoolDto;
import com.tps.tpi.model.objects.dto.AddressDto;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts an School entity to an SchoolDto dto
 */
public class SchoolConverter extends AbstractReferenceDataConverter {

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof School && dto instanceof SchoolDto) {
            School entity = (School) source;

            if (entity.getAddress() != null) {
                ((SchoolDto) dto).setAddress(mapper.map(entity.getAddress(), AddressDto.class));
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
            if (source instanceof SchoolDto && entity instanceof School) {
                SchoolDto dto = (SchoolDto) source;

                if (dto.getAddress() != null) {
                    ((School) entity).setAddress(mapper.map(dto.getAddress(), Address.class));
                }
            }

        return entity;
    }
}