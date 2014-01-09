package com.tps.tpi.converter;

import com.tps.tpi.dao.PositionDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Endorsement;
import com.tps.tpi.model.objects.entity.Position;
import com.tps.tpi.model.objects.entity.PositionEndorsement;
import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.EndorsementDto;
import com.tps.tpi.model.objects.dto.PositionEndorsementDto;
import org.apache.commons.lang.StringUtils;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a PositionEndorsement entity to a PositionEndorsementDto dto.
 */
public class PositionEndorsementConverter extends AbstractConverter {
    private final static Logger log = LoggerFactory.getLogger(PositionEndorsementConverter.class);

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof PositionEndorsement && dto instanceof PositionEndorsementDto) {
            PositionEndorsement entity = (PositionEndorsement) source;

            ((PositionEndorsementDto) dto).setEndorsement(mapper.map(entity.getEndorsement(), EndorsementDto.class));

            if (entity.getPosition() != null) {
                ((PositionEndorsementDto) dto).setPosition(entity.getPosition().getId());
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {

        try {
            if (source instanceof PositionEndorsementDto && entity instanceof PositionEndorsement) {
                PositionEndorsementDto dto = (PositionEndorsementDto) source;

                ((PositionEndorsement) entity).setEndorsement(mapper.map(dto.getEndorsement(), Endorsement.class));

                if (StringUtils.isNotBlank(dto.getPosition())) {
                    ((PositionEndorsement) entity).setPosition(positionDao.get(Position.class, dto.getPosition()));
                }
            }
        } catch (PersistenceException e) {
            log.error("Could not retrieve entity object from db: " + e.getMessage(), e);
            throw new MappingException(e.getMessage(), e);
        }

        return entity;
    }

    @Autowired
    private PositionDao positionDao;

}