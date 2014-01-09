package com.tps.tpi.converter;

import com.tps.tpi.dao.HistoryDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Project entity to a ProjectDto dto.
 */
public class ProjectConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(ProjectConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Project && dto instanceof ProjectDto) {
            Project entity = (Project) source;

            ((ProjectDto) dto).setFrom(entity.getFrom());
            ((ProjectDto) dto).setName(entity.getName());
            ((ProjectDto) dto).setTo(entity.getTo());

            if (entity.getCity() != null) {
                ((ProjectDto) dto).setCity(mapper.map(entity.getCity(), CityDto.class));
            }

            if (entity.getCompany() != null) {
                ((ProjectDto) dto).setCompany(mapper.map(entity.getCompany(), CompanyDto.class));
            }

            if (entity.getSkilledRole() != null) {
                ((ProjectDto) dto).setSkilledRole(mapper.map(entity.getSkilledRole(), SkilledRoleDto.class));
            }

            if (entity.getProjectEndorsements() != null) {
                for (ProjectEndorsement endorsement : entity.getProjectEndorsements()) {
                    ((ProjectDto) dto).addProjectEndorsement(mapper.map(endorsement, ProjectEndorsementDto.class));
                }
            }

            if (entity.getHistory() != null) {
                ((ProjectDto) dto).setHistory(entity.getHistory().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof ProjectDto && entity instanceof Project) {
                ProjectDto dto = (ProjectDto) source;

                ((Project) entity).setFrom(dto.getFrom());
                ((Project) entity).setName(dto.getName());
                ((Project) entity).setTo(dto.getTo());

                if (dto.getCity() != null) {
                    ((Project) entity).setCity(mapper.map(dto.getCity(), City.class));
                }

                if (dto.getCompany() != null) {
                    ((Project) entity).setCompany(mapper.map(dto.getCompany(), Company.class));
                }

                if (dto.getSkilledRole() != null) {
                    ((Project) entity).setSkilledRole(mapper.map(dto.getSkilledRole(), SkilledRole.class));
                }

                if (StringUtils.isNotBlank(dto.getHistory())) {
                    ((Project) entity).setHistory(historyDao.get(History.class, dto.getHistory()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private HistoryDao historyDao;

}