package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.Department;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Department entity to a DepartmentDto dto
 */
public class DepartmentConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(DepartmentConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Department && dto instanceof DepartmentDto) {
            Department entity = (Department) source;

            if (entity.getDivision() != null) {
                ((DepartmentDto) dto).setDivision(entity.getDivision().getId());
            }

            if (entity.getParent() != null) {
                ((DepartmentDto) dto).setParent(entity.getParent().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
//        try {
            if (source instanceof DepartmentDto && entity instanceof Department) {
                DepartmentDto dto = (DepartmentDto) source;

                // we're not going to be creating departments with dtos so this can be empty
            }
//        } catch (PersistenceException e) {
//            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
//            throw new MappingException(e.getMessage(), e);
//        }

        return entity;
    }
}