package com.tps.tpi.converter;

import com.tps.tpi.dao.BiographyDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Biography;
import com.tps.tpi.model.objects.entity.BiographyCity;
import com.tps.tpi.model.objects.entity.City;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.BiographyCityDto;
import com.tps.tpi.model.objects.dto.CityDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a BiographyCity entity to a BiographyCityDto dto.
 */
public class BiographyCityConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(BiographyCityConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof BiographyCity && dto instanceof BiographyCityDto) {
            BiographyCity entity = (BiographyCity) source;

            if (entity.getCity() != null) {
                ((BiographyCityDto) dto).setCity(mapper.map(entity.getCity(), CityDto.class));
            }

            if (entity.getBiography() != null) {
                ((BiographyCityDto) dto).setBiography(entity.getBiography().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof BiographyCityDto && entity instanceof BiographyCity) {
                BiographyCityDto dto = (BiographyCityDto) source;

                if (dto.getCity() != null) {
                    ((BiographyCity) entity).setCity(mapper.map(dto.getCity(), City.class));
                }

                if (StringUtils.isNotBlank(dto.getBiography())) {
                    ((BiographyCity) entity).setBiography(biographyDao.get(Biography.class, dto.getBiography()));
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