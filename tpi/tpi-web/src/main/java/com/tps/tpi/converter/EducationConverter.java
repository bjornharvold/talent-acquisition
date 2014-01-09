package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Education entity to a EducationDto dto.
 */
public class EducationConverter extends AbstractConverter {

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Education && dto instanceof EducationDto) {
            Education entity = (Education) source;

            ((EducationDto) dto).setPerson(entity.getPerson().getId());
            
            if (entity.getDegrees() != null) {
                for (Degree tt : entity.getDegrees()) {
                    ((EducationDto) dto).addDegree(mapper.map(tt, DegreeDto.class));
                }
            }

            if (entity.getEducationCertifications() != null) {
                for (EducationCertification tt : entity.getEducationCertifications()) {
                    ((EducationDto) dto).addEducationCertification(mapper.map(tt, EducationCertificationDto.class));
                }
            }

            if (entity.getLanguages() != null) {
                for (Language tt : entity.getLanguages()) {
                    ((EducationDto) dto).addLanguage(mapper.map(tt, LanguageDto.class));
                }
            }

        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof EducationDto && entity instanceof Education) {
            EducationDto dto = (EducationDto) source;

        }

        return entity;
    }
}