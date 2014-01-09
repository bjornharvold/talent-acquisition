package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Biography entity to a BiographyDto dto.
 */
public class BiographyConverter extends AbstractConverter {

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Biography && dto instanceof BiographyDto) {
            Biography entity = (Biography) source;

            ((BiographyDto) dto).setSummary(entity.getSummary());
            ((BiographyDto) dto).setPerson(entity.getPerson().getId());
            
            if (entity.getAwards() != null) {
                for (Award tt : entity.getAwards()) {
                    ((BiographyDto) dto).addAward(mapper.map(tt, AwardDto.class));
                }
            }

            if (entity.getBiographyCities() != null) {
                for (BiographyCity tt : entity.getBiographyCities()) {
                    ((BiographyDto) dto).addBiographyCity(mapper.map(tt, BiographyCityDto.class));
                }
            }

            if (entity.getBiographyDepartments() != null) {
                for (BiographyDepartment tt : entity.getBiographyDepartments()) {
                    ((BiographyDto) dto).addBiographyDepartment(mapper.map(tt, BiographyDepartmentDto.class));
                }
            }

            if (entity.getBiographySkilledRoles() != null) {
                for (BiographySkilledRole tt : entity.getBiographySkilledRoles()) {
                    ((BiographyDto) dto).addBiographySkilledRole(mapper.map(tt, BiographySkilledRoleDto.class));
                }
            }

            if (entity.getBiographySkills() != null) {
                for (BiographySkill tt : entity.getBiographySkills()) {
                    ((BiographyDto) dto).addBiographySkill(mapper.map(tt, BiographySkillDto.class));
                }
            }

            if (entity.getPatents() != null) {
                for (Patent tt : entity.getPatents()) {
                    ((BiographyDto) dto).addPatent(mapper.map(tt, PatentDto.class));
                }
            }

            if (entity.getPublications() != null) {
                for (Publication tt : entity.getPublications()) {
                    ((BiographyDto) dto).addPublication(mapper.map(tt, PublicationDto.class));
                }
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof BiographyDto && entity instanceof Biography) {
            BiographyDto dto = (BiographyDto) source;

            ((Biography) entity).setSummary(dto.getSummary());
        }

        return entity;
    }
}