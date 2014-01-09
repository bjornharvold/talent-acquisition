package com.tps.tpi.converter;

import com.tps.tpi.dao.BiographyDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Biography;
import com.tps.tpi.model.objects.entity.BiographyDepartment;
import com.tps.tpi.model.objects.entity.Department;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.BiographyDepartmentDto;
import com.tps.tpi.model.objects.dto.DepartmentDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a BiographyDepartment entity to a BiographyDepartmentDto dto.
 */
public class BiographyDepartmentConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(BiographyDepartmentConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof BiographyDepartment && dto instanceof BiographyDepartmentDto) {
            BiographyDepartment entity = (BiographyDepartment) source;

            ((BiographyDepartmentDto) dto).setYears(entity.getYears());

            if (entity.getDepartment() != null) {
                ((BiographyDepartmentDto) dto).setDepartment(mapper.map(entity.getDepartment(), DepartmentDto.class));
            }

            if (entity.getBiography() != null) {
                ((BiographyDepartmentDto) dto).setBiography(entity.getBiography().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof BiographyDepartmentDto && entity instanceof BiographyDepartment) {
                BiographyDepartmentDto dto = (BiographyDepartmentDto) source;

                ((BiographyDepartment) entity).setYears(dto.getYears());

                if (dto.getDepartment() != null) {
                    ((BiographyDepartment) entity).setDepartment(mapper.map(dto.getDepartment(), Department.class));
                }

                if (StringUtils.isNotBlank(dto.getBiography())) {
                    ((BiographyDepartment) entity).setBiography(biographyDao.get(Biography.class, dto.getBiography()));
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