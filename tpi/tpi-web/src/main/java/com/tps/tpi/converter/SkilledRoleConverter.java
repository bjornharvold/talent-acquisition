package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractReferenceDataEntity;
import com.tps.tpi.model.objects.entity.SkilledRole;
import com.tps.tpi.model.objects.dto.AbstractReferenceDataDto;
import com.tps.tpi.model.objects.dto.SkilledRoleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a SkilledRole entity to a SkilledRoleDto dto
 */
public class SkilledRoleConverter extends AbstractReferenceDataConverter {
    private final static Logger log = LoggerFactory.getLogger(SkilledRoleConverter.class);

    @Override
    protected AbstractReferenceDataDto toDto(AbstractReferenceDataDto dto, AbstractReferenceDataEntity source, Class destClass, Class sourceClass) {
        if (source instanceof SkilledRole && dto instanceof SkilledRoleDto) {
            SkilledRole entity = (SkilledRole) source;

            if (entity.getSkilledRoleGroup() != null) {
                ((SkilledRoleDto) dto).setSkilledRoleGroup(entity.getSkilledRoleGroup().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractReferenceDataEntity toEntity(AbstractReferenceDataEntity entity, AbstractReferenceDataDto source, Class destClass, Class sourceClass) {
//        try {
            if (source instanceof SkilledRoleDto && entity instanceof SkilledRole) {
                SkilledRoleDto dto = (SkilledRoleDto) source;

                // we're not going to be creating skilled role with dtos so this can be empty
            }
//        } catch (PersistenceException e) {
//            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
//            throw new MappingException(e.getMessage(), e);
//        }

        return entity;
    }
}