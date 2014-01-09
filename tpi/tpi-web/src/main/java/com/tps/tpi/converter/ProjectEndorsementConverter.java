package com.tps.tpi.converter;

import com.tps.tpi.dao.ProjectDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Endorsement;
import com.tps.tpi.model.objects.entity.Project;
import com.tps.tpi.model.objects.entity.ProjectEndorsement;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.EndorsementDto;
import com.tps.tpi.model.objects.dto.ProjectEndorsementDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a ProjectEndorsement entity to a ProjectEndorsementDto dto.
 */
public class ProjectEndorsementConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(ProjectEndorsementConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof ProjectEndorsement && dto instanceof ProjectEndorsementDto) {
            ProjectEndorsement entity = (ProjectEndorsement) source;

            ((ProjectEndorsementDto) dto).setEndorsement(mapper.map(entity.getEndorsement(), EndorsementDto.class));

            if (entity.getProject() != null) {
                ((ProjectEndorsementDto) dto).setProject(entity.getProject().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof ProjectEndorsementDto && entity instanceof ProjectEndorsement) {
                ProjectEndorsementDto dto = (ProjectEndorsementDto) source;

                ((ProjectEndorsement) entity).setEndorsement(mapper.map(dto.getEndorsement(), Endorsement.class));

                if (StringUtils.isNotBlank(dto.getProject())) {
                    ((ProjectEndorsement) entity).setProject(projectDao.get(Project.class, dto.getProject()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private ProjectDao projectDao;

}