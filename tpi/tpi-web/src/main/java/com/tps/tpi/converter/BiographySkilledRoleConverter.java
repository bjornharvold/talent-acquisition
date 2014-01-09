package com.tps.tpi.converter;

import com.tps.tpi.dao.BiographyDao;
import com.tps.tpi.dao.SkilledRoleDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Biography;
import com.tps.tpi.model.objects.entity.BiographySkilledRole;
import com.tps.tpi.model.objects.entity.SkilledRole;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.BiographySkilledRoleDto;
import com.tps.tpi.model.objects.dto.SkilledRoleDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a BiographySkilledRole entity to a BiographySkilledRoleDto dto.
 */
public class BiographySkilledRoleConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(BiographySkilledRoleConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof BiographySkilledRole && dto instanceof BiographySkilledRoleDto) {
            BiographySkilledRole entity = (BiographySkilledRole) source;

            ((BiographySkilledRoleDto) dto).setSkilledRole(mapper.map(entity.getSkilledRole(), SkilledRoleDto.class));
            ((BiographySkilledRoleDto) dto).setYears(entity.getYears());

            if (entity.getBiography() != null) {
                ((BiographySkilledRoleDto) dto).setBiography(entity.getBiography().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof BiographySkilledRoleDto && entity instanceof BiographySkilledRole) {
                BiographySkilledRoleDto dto = (BiographySkilledRoleDto) source;

                ((BiographySkilledRole) entity).setSkilledRole(mapper.map(dto.getSkilledRole(), SkilledRole.class));
                ((BiographySkilledRole) entity).setYears(dto.getYears());

                if (StringUtils.isNotBlank(dto.getBiography())) {
                    ((BiographySkilledRole) entity).setBiography(biographyDao.get(Biography.class, dto.getBiography()));
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

    @Autowired
    private SkilledRoleDao skilledRoleDao;

}