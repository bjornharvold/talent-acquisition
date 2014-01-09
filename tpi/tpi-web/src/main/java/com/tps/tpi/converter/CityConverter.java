package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.City;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.CityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a City entity to a CityDto dto
 */
public class CityConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(CityConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof City && dto instanceof CityDto) {
            City entity = (City) source;

            if (entity.getCountry() != null) {
                ((CityDto) dto).setCountry(entity.getCountry().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
//        try {
            if (source instanceof CityDto && entity instanceof City) {
                CityDto dto = (CityDto) source;

                // we're not going to be creating cities with dtos so this can be empty
            }
//        } catch (PersistenceException e) {
//            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
//            throw new MappingException(e.getMessage(), e);
//        }

        return entity;
    }
}