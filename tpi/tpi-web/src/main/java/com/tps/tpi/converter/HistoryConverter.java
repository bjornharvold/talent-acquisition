package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a History entity to a HistoryDto dto.
 */
public class HistoryConverter extends AbstractConverter {

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof History && dto instanceof HistoryDto) {
            History entity = (History) source;

            ((HistoryDto) dto).setPerson(entity.getPerson().getId());
            
            if (entity.getAffiliations() != null) {
                for (Affiliation tt : entity.getAffiliations()) {
                    ((HistoryDto) dto).addAffiliation(mapper.map(tt, AffiliationDto.class));
                }
            }

            if (entity.getCoverages() != null) {
                for (Coverage tt : entity.getCoverages()) {
                    ((HistoryDto) dto).addCoverage(mapper.map(tt, CoverageDto.class));
                }
            }

            if (entity.getPositions() != null) {
                for (Position tt : entity.getPositions()) {
                    ((HistoryDto) dto).addPosition(mapper.map(tt, PositionDto.class));
                }
            }

            if (entity.getProjects() != null) {
                for (Project tt : entity.getProjects()) {
                    ((HistoryDto) dto).addProject(mapper.map(tt, ProjectDto.class));
                }
            }

        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        if (source instanceof HistoryDto && entity instanceof History) {
            HistoryDto dto = (HistoryDto) source;

        }

        return entity;
    }
}