package com.tps.tpi.converter;

import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.CityDto;
import com.tps.tpi.model.objects.dto.CountryDto;
import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.City;
import com.tps.tpi.model.objects.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Country entity to a CountryDto dto
 */
public class CountryConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(CountryConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Country && dto instanceof CountryDto) {
            Country entity = (Country) source;

            if (entity.getCities() != null) {
                for (City city : entity.getCities()) {
                    ((CountryDto) dto).addCity(mapper.map(city, CityDto.class));
                }
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
//        try {
            if (source instanceof CountryDto && entity instanceof Country) {
                CountryDto dto = (CountryDto) source;

                // we're not going to be creating cities with dtos so this can be empty
            }
//        } catch (PersistenceException e) {
//            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
//            throw new MappingException(e.getMessage(), e);
//        }

        return entity;
    }
}