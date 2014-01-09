package com.tps.tpi.converter;

import com.tps.tpi.model.objects.dto.CountryDto;
import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.Country;
import com.tps.tpi.model.objects.entity.Region;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.RegionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Region entity to a RegionDto dto
 */
public class RegionConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(RegionConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Region && dto instanceof RegionDto) {
            Region entity = (Region) source;

            if (entity.getParent() != null) {
                ((RegionDto) dto).setParent(entity.getParent().getId());
                ((RegionDto) dto).setParentName(entity.getParent().getCode());
            }
            if (entity.getChildren() != null) {
                for (Region region : entity.getChildren()) {
                    ((RegionDto) dto).addChild(mapper.map(region, RegionDto.class));
                }
            }
            if (entity.getCountries() != null) {
                for (Country country : entity.getCountries()) {
                    ((RegionDto) dto).addCountry(mapper.map(country, CountryDto.class));
                }
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
//        try {
            if (source instanceof RegionDto && entity instanceof Region) {
                RegionDto dto = (RegionDto) source;

                // we're not going to be creating regions with dtos so this can be empty
            }
//        } catch (PersistenceException e) {
//            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
//            throw new MappingException(e.getMessage(), e);
//        }

        return entity;
    }
}